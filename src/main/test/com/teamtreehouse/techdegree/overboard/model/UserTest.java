package com.teamtreehouse.techdegree.overboard.model;

import com.teamtreehouse.techdegree.overboard.exc.AnswerAcceptanceException;
import com.teamtreehouse.techdegree.overboard.exc.VotingException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class UserTest {
    Board board;
    User questioner;
    User answerer;
    Question question;
    Answer answer;

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
        try {
            answerer.acceptAnswer(answer);
        } catch (AnswerAcceptanceException aae) {
            assertEquals("Only Questioner can accept this answer as it is their question", aae.getMessage());
        }
    }

    @Test
    public void acceptingAnswerToSelfAuthoredQuestionAcceptsAnswer() {
        // TODO:bhs - Write this test
    }

}

/*
    Some Notes:
        1) Write test method names as <action><consequence>


    Testing Standard: AAA
        1) Arrange - Setup the test scenario
        2) Act - Perform the action you wish to test
        3) Assert - Assert the behavior you desire has occured

        - Blank line separates each aspect of the test
        - Try to shoot for one assertion per test
            -> asserts work like exceptions, cancel immediately at failure
        - Each behavior under test should be ISOLATED
            -> Could be OK to have two asserts in one test if they describe the same behavior


    Fixtures
        - When you kick off test runner (TR), the TR collects all methods annotated
          with @Test
        - An object instance is then created for each collected method and the methods are executed
        - All of these objects can access fields on the primary test class
        - Using "Fixtures", or annotations which denote fields to be shared among test methods,
          we can remove duplicated code by letting each test method use the same
          setup and teardown instructions (@Before and @After).


    FIRST Rule for Test Writing
        - Fast
        - Isolated
        - Repeatable
        - Self-verifying
        - Timely

    How to Find Boundary Conditions - CORRECT:
        - Date doesn't CONFORM
        - How does the ORDERING impact your function
        - Check the RANGE
        - Does the unit REFERENCE other code?
        - EXISTENCE - can things be null?
        - How does the number of elements in a collection, CARDINALITY, affect you? (0, 1, N)
        - How does TIME influence you?

    What not to Test?
        - Don't test things that don't normally break (e.g. getters/setters)
        - JUnit -> "Test until fears turns to boredom"
        -
 */