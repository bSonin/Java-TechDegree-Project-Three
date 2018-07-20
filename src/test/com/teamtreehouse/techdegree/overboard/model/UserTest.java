package com.teamtreehouse.techdegree.overboard.model;

import com.teamtreehouse.techdegree.overboard.exc.AnswerAcceptanceException;
import com.teamtreehouse.techdegree.overboard.exc.VotingException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class UserTest {
    Board board;
    User questioner;
    User answerer;
    Question question;
    Answer answer;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() {
        board = new Board("Testing");
        questioner = new User(board, "Questioner");
        answerer = new User(board, "Answerer");

        question = questioner.askQuestion("Is one equal to one?");
        answer = answerer.answerQuestion(question, "yes!");
    }

    @Test
    public void upVotingQuestionIncreasesReputationOfQuestionerByFive() {
        User voter = new User(board,"Voter");

        voter.upVote(question);

        assertEquals(5, questioner.getReputation());
    }

    @Test
    public void upVotingAnswerIncreasesReputationOfAnswererByTen() {
        User voter = new User(board, "Voter");

        voter.upVote(answer);

        assertEquals(10, answerer.getReputation());
    }

    @Test
    public void acceptingAnswerIncreasesAnswerersReputationByFifteen() {
        // Arrangement handled in @Before method

        questioner.acceptAnswer(answer);

        assertEquals(15, answerer.getReputation());
    }

    @Test(expected = VotingException.class)
    public void upVotingSelfAuthoredPostThrowsException() throws Exception {
        // upVote takes abstract Post - only need to test answer or question
        answerer.upVote(answer);

    }

    @Test(expected = VotingException.class)
    public void downVotingSelfAuthoredPostThrowsException() throws Exception {
        // downVote takes abstract Post - only need to test answer or question
        questioner.downVote(question);
    }

    @Test
    public void acceptingAnswerForAnotherUsersQuestionThrowsExceptionWithMessage() {
        exception.expect(AnswerAcceptanceException.class);
        exception.expectMessage("Only Questioner can accept this answer as it is their question");

        answerer.acceptAnswer(answer);

    }

    @Test
    public void acceptingAnswerToSelfAuthoredQuestionAcceptsAnswer() {
        questioner.acceptAnswer(answer);

        assertEquals(true, question.getAnswers().get(0).isAccepted());
    }

    @Test
    public void downVotingQuestionDecreasesQuestionersReputationByTwo() {
        User voter = new User(board, "Voter");

        voter.downVote(question);

        assertEquals(-2, questioner.getReputation());
    }

}
