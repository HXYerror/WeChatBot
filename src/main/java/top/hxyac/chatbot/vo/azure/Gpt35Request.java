package top.hxyac.chatbot.vo.azure;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Gpt35Request {


        private List<Gpt35Message> messages;
        private int max_tokens;
        private double temperature;
        private int frequency_penalty;
        private int presence_penalty;
        private double top_p;
        private String stop;

        public void setGpt35Message(List<Gpt35Message> messages) {
            this.messages = messages;
        }
        @JsonProperty("messages")
        public List<Gpt35Message> getGpt35Message() {
            return messages;
        }

        public void setMax_tokens(int max_tokens) {
            this.max_tokens = max_tokens;
        }
        public int getMax_tokens() {
            return max_tokens;
        }

        public void setTemperature(double temperature) {
            this.temperature = temperature;
        }
        public double getTemperature() {
            return temperature;
        }

        public void setFrequency_penalty(int frequency_penalty) {
            this.frequency_penalty = frequency_penalty;
        }
        public int getFrequency_penalty() {
            return frequency_penalty;
        }

        public void setPresence_penalty(int presence_penalty) {
            this.presence_penalty = presence_penalty;
        }
        public int getPresence_penalty() {
            return presence_penalty;
        }

        public void setTop_p(double top_p) {
            this.top_p = top_p;
        }
        public double getTop_p() {
            return top_p;
        }

        public void setStop(String stop) {
            this.stop = stop;
        }
        public String getStop() {
            return stop;
        }

    @Override
    public String toString() {
        return "Gpt35Request{" +
                "messages=" + messages.toString() +
                ", max_tokens=" + max_tokens +
                ", temperature=" + temperature +
                ", frequency_penalty=" + frequency_penalty +
                ", presence_penalty=" + presence_penalty +
                ", top_p=" + top_p +
                ", stop='" + stop + '\'' +
                '}';
    }
}