package pcube.servey.database;

public class PostQuestionPost {
    String question,optiona,optionb,optionc,optiond,answer,type;
    String id;

    public PostQuestionPost(String id, String question, String optiona, String optionb, String optionc, String optiond,String answer,String type) {
        this.id = id;
        this.question = question;
        this.optiona = optiona;
        this.optionb = optionb;
        this.optionc = optionc;
        this.optiond = optiond;
        this.answer = answer;
        this.type = type;
    }
}
