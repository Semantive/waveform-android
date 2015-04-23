/*
 * Copyright (C) 2008 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.semantive.waveformandroid.waveform.soundfile;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;

/**
 * CheapWAV represents a standard 16-bit WAV file, splitting it into
 * artificial frames of 20 ms and taking the maximum of each frame to
 * get an approximation of the waveform contour.
 *
 * Modified by Anna Stępień <anna.stepien@semantive.com>
 */
public class CheapWAV extends CheapSoundFile {
    public static final String TAG = "CheapWAV";

    public static Factory getFactory() {
        return new Factory() {
            public CheapSoundFile create() {
                return new CheapWAV();
            }
            public String[] getSupportedExtensions() {
                return new String[] { "wav" };
            }
        };
    }

    // Member variables containing frame info
    private int mNumFrames;
    private int[] mFrameGains;
    private int mFrameBytes;
    private int mFileSize;
    private int mSampleRate;
    private int mChannels;
    // Member variables used during initialization
    private int mOffset;

    public CheapWAV() {
    }

    public int getNumFrames() {
        return mNumFrames;
    }

    public int getSamplesPerFrame() {
        return mSampleRate / 50;
    }

    public int[] getFrameGains() {
        return mFrameGains;
    }

    public int getFileSizeBytes() {
        return mFileSize;        
    }

    public int getAvgBitrateKbps() {
        return mSampleRate * mChannels * 2 / 1024;
    }

    public int getSampleRate() {
        return mSampleRate;
    }

    public int getChannels() {
        return mChannels;
    }

    public String getFiletype() {
        return "WAV";
    }

    public void ReadFile(File inputFile) throws java.io.FileNotFoundException, java.io.IOException {
        super.ReadFile(inputFile);
        mFileSize = (int)mInputFile.length();

        if (mFileSize < 128) {
            throw new java.io.IOException("File too small to parse");
        }

        FileInputStream stream = new FileInputStream(mInputFile);
        byte[] header = new byte[12];
        stream.read(header, 0, 12);
        mOffset += 12;
        if (header[0] != 'R' ||
            header[1] != 'I' ||
            header[2] != 'F' ||
            header[3] != 'F' ||
            header[8] != 'W' ||
            header[9] != 'A' ||
            header[10] != 'V' ||
            header[11] != 'E') {
            throw new java.io.IOException("Not a WAV file");
        }

        mChannels = 0;
        mSampleRate = 0;
        while (mOffset + 8 <= mFileSize) {
            byte[] chunkHeader = new byte[8];
            stream.read(chunkHeader, 0, 8);
            mOffset += 8;

            int chunkLen =
                ((0xff & chunkHeader[7]) << 24) |
                ((0xff & chunkHeader[6]) << 16) |
                ((0xff & chunkHeader[5]) << 8) |
                ((0xff & chunkHeader[4]));

            if (chunkHeader[0] == 'f' &&
                chunkHeader[1] == 'm' &&
                chunkHeader[2] == 't' &&
                chunkHeader[3] == ' ') {
                if (chunkLen < 16 || chunkLen > 1024) {
                    throw new java.io.IOException("WAV file has bad fmt chunk");
                }

                byte[] fmt = new byte[chunkLen];
                stream.read(fmt, 0, chunkLen);
                mOffset += chunkLen;

                int format =
                    ((0xff & fmt[1]) << 8) |
                    ((0xff & fmt[0]));
                mChannels =
                    ((0xff & fmt[3]) << 8) |
                    ((0xff & fmt[2]));
                mSampleRate =
                    ((0xff & fmt[7]) << 24) |
                    ((0xff & fmt[6]) << 16) |
                    ((0xff & fmt[5]) << 8) |
                    ((0xff & fmt[4]));

                if (format != 1) {
                    throw new java.io.IOException("Unsupported WAV file encoding");
                }

            } else if (chunkHeader[0] == 'd' &&
                       chunkHeader[1] == 'a' &&
                       chunkHeader[2] == 't' &&
                       chunkHeader[3] == 'a') {
                if (mChannels == 0 || mSampleRate == 0) {
                    throw new java.io.IOException("Bad WAV file: data chunk before fmt chunk");
                }

                int frameSamples = (mSampleRate * mChannels) / 50;
                mFrameBytes = frameSamples * 2;

                mNumFrames = (chunkLen + (mFrameBytes - 1)) / mFrameBytes;
                Log.d(TAG, String.format("Number of frames: %d", mNumFrames));
                mFrameGains = new int[mNumFrames];

                byte[] oneFrame = new byte[mFrameBytes];

                int i = 0;
                int frameIndex = 0;
                while (i < chunkLen) {
                    int oneFrameBytes = mFrameBytes;
                    if (i + oneFrameBytes > chunkLen) {
                        i = chunkLen - oneFrameBytes;
                    }

                    stream.read(oneFrame, 0, oneFrameBytes);

                    int maxGain = 0;
                    for (int j = 1; j < oneFrameBytes; j += 4 * mChannels) {
                        int val = Math.abs(oneFrame[j]);
                        if (val > maxGain) {
                            maxGain = val;
                        }
                    }

                    mFrameGains[frameIndex] = maxGain;

                    frameIndex++;
                    mOffset += oneFrameBytes;
                    i += oneFrameBytes;

                    if (mProgressListener != null) {
                        boolean keepGoing = mProgressListener.reportProgress(i * 1.0 / chunkLen);
                        if (!keepGoing) {
                            break;
                        }
                    }
                }

            } else {
                stream.skip(chunkLen);
                mOffset += chunkLen;
            }
        }
    }
}
