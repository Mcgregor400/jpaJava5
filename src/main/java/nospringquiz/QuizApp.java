package nospringquiz;
import entity.Option;
import entity.Question;
import entity.Quiz;
import jpa.MyPersistence;import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;public class QuizApp {
    public static void initData(QuestionRepository repository,QuizRepository quizRepository){
        Set<Question> questions = new HashSet<>();
        Question q = Question.builder()
                .body("Wybierz słowo kluczowe Javy")
                .options(Option.builder()
                        .option1("Char")
                        .option2("integer")
                        .option3("boolean")
                        .option4("real")
                        .build())
                .validOption(3)
                .points(5)
                .build();
        questions.add(q);
        repository.save(q);
        q = Question.builder()
                .body("Wskaż instrukcję przerywającą iteracje.")
                .options(Option.builder()
                        .option1("switch")
                        .option2("return")
                        .option3("continue")
                        .option4("break")
                        .build())
                .validOption(4)
                .points(5)
                .build();
        questions.add(q);
        repository.save(q);
        q = Question.builder()
                .body("Które wyrażenie jest fałszem")
                .options(Option.builder()
                        .option1("10 > 5 && true")
                        .option2("\"a\".equals(\"a\")")
                        .option3("true == false")
                        .option4("10 != 4")
                        .build())
                .validOption(3)
                .points(5)
                .build();
        questions.add(q);
        repository.save(q);        Quiz quiz = Quiz.builder().title("Język Java").questions(questions).build();
        quizRepository.save(quiz);
    }
    public static void main(String[] args) {
        QuestionRepository questionRepository = new QuestionRepositoryJpa(MyPersistence.QUIZ);
        QuizRepository quizRepository = new QuizRepositoryJpa(MyPersistence.QUIZ);
        initData(questionRepository,quizRepository);
        QuizService quizService = new QuizServiceJpa(quizRepository);
        QuizController controller = new QuizController(quizService,1);
        Scanner scanner = new Scanner(System.in);
        while(true){
            Question question = controller.next();
            System.out.println(question.getBody());
            System.out.println("1. " + question.getOptions().getOption1());
            System.out.println("2. " + question.getOptions().getOption2());
            System.out.println("3. " + question.getOptions().getOption3());
            System.out.println("4. " + question.getOptions().getOption4());
            System.out.println("0. Cofnij się do poprzedniego pytania");
            System.out.println("5. Koniec");
            int answer = scanner.nextInt();
            if (answer == 0){
                controller.previous();
                controller.previous();
                continue;
            }
            if (answer == 5){
                break;
            }
            controller.saveAnswer(question, answer);
        }
        System.out.println("Podsumowanie quizu: " + controller.summary());
    }
}