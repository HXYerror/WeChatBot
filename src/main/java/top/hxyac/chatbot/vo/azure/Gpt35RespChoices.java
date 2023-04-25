package top.hxyac.chatbot.vo.azure;

public class Gpt35RespChoices {

        private Gpt35Message message;
        private String finish_reason;
        private int index;
        public void setMessage(Gpt35Message message) {
            this.message = message;
        }
        public Gpt35Message getMessage() {
            return message;
        }

        public void setFinish_reason(String finish_reason) {
            this.finish_reason = finish_reason;
        }
        public String getFinish_reason() {
            return finish_reason;
        }

        public void setIndex(int index) {
            this.index = index;
        }
        public int getIndex() {
            return index;
        }

    }