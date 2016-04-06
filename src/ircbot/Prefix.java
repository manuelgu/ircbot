package ircbot;

public enum Prefix {
    DOT {
        @Override
        public String toString() {
            return ".";
        }
    },
    QUESTION_MARK {
        @Override
        public String toString() {
            return "?";
        }
    },
    EXCLAMATION_MARK {
        @Override
        public String toString() {
            return "!";
        }
    };

}
