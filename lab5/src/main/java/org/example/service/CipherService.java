package org.example.service;

import java.io.*;

public class CipherService {


    public void encrypt(InputStream input, OutputStream output, char key) throws IOException {
        try (var encryptedStream = new EncryptionFilterOutputStream(output, key);
             var reader = new InputStreamReader(input);
             var writer = new OutputStreamWriter(encryptedStream)) {

            reader.transferTo(writer);
            writer.flush();
        }
    }

    public void decrypt(InputStream input, OutputStream output, char key) throws IOException {
        try (var decryptedStream = new DecryptionFilterInputStream(input, key);
             var reader = new InputStreamReader(decryptedStream);
             var writer = new OutputStreamWriter(output)) {

            reader.transferTo(writer);
            writer.flush();
        }
    }

    /**
     * Custom FilterOutputStream for encryption
     */
    private static class EncryptionFilterOutputStream extends FilterOutputStream {
        private final int keyCode;

        public EncryptionFilterOutputStream(OutputStream out, char key) {
            super(out);
            this.keyCode = key;
        }

        @Override
        public void write(int b) throws IOException {
            // Encrypt by adding key code to byte value
            super.write((b + keyCode) & 0xFF);
        }

        @Override
        @SuppressWarnings("NullableProblems")
        public void write(byte[] b, int off, int len) throws IOException {
            for (int i = 0; i < len; i++) {
                write(b[off + i]);
            }
        }
    }

    private static class DecryptionFilterInputStream extends FilterInputStream {
        private final int keyCode;

        public DecryptionFilterInputStream(InputStream in, char key) {
            super(in);
            this.keyCode = key;
        }

        @Override
        public int read() throws IOException {
            int b = super.read();
            if (b == -1) {
                return -1;
            }
            // Decrypt by subtracting key code from byte value
            return (b - keyCode) & 0xFF;
        }

        @Override
        @SuppressWarnings("NullableProblems")
        public int read(byte[] b, int off, int len) throws IOException {
            int bytesRead = super.read(b, off, len);
            if (bytesRead == -1) {
                return -1;
            }
            // Decrypt each byte
            for (int i = 0; i < bytesRead; i++) {
                b[off + i] = (byte) ((b[off + i] - keyCode) & 0xFF);
            }
            return bytesRead;
        }
    }
}

