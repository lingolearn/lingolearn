package cscie99.team2.lingolearn.server.confuser;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import cscie99.team2.lingolearn.shared.Card;
import cscie99.team2.lingolearn.shared.error.ConfuserException;

/**
 * This class tests to make sure that the values being generated by the confuser
 * algorithm are valid.
 */
public class ConfuserTest {
	// The confuser instance to use for tests
	private static Confuser confuser;

	/**
	 * Make sure the confuser algorithm code is using the correct data.
	 */
	@Before
	public void setUp() {
		ConfuserTest.confuser = new Confuser();
	}

	/**
	 * This helper function checks to ensure that the results provided
	 * match the expected results.
	 * 
	 * @param results The results from the test.
	 * @param expected The reference set of expected results.
	 */
	private void checkResults(List<String> results, String[] expected) {
		assertEquals(expected.length, results.size());
		for (String phrase : expected) {
			assertEquals(true, results.contains(phrase));
		}
	}
	
	/**
	 * Test to make sure vowels are elongated correctly for hiragana words.
	 */
	@Test
	public void hiraganaVowelElongationTest() {
		// Test to make sure え (picture) is extended correctly into ええ (yes)
		List<String> results = confuser.getHiraganaManipulation("え");
		assertEquals(1, results.size());
		assertEquals("ええ", results.get(0));
		
		// Test to make sure おばさん (aunt) is extended correctly
		String expected[] = new String[] { "おおばさん", "おばあさん", "おばさあん" };
		checkResults(confuser.getHiraganaManipulation("おばさん"), expected);
		
		// Test to make sure おばあさん (grandmother) is extended correctly
		expected = new String[] { "おおばあさん", "おばあさあん" };
		checkResults(confuser.getHiraganaManipulation("おばあさん"), expected);
		
		// Test to make sure おじさん (uncle) is extended correctly
		expected = new String[] { "おおじさん", "おじいさん", "おじさあん" };
		checkResults(confuser.getHiraganaManipulation("おじさん"), expected);
		
		// Test to make sure おじいさん (grandfather) is extended correctly
		expected = new String[] { "おおじいさん", "おじいさあん" };
		checkResults(confuser.getHiraganaManipulation("おじいさん"), expected);
	}
	
	/**
	 * Test to make sure that we can extend kanji with valid hiragana.
	 */
	@Test
	public void kanjiBoundriesTest() throws ConfuserException {
		// Test to make sure 食べ難い (difficult to eat) is extended correctly
		Card card = new Card();
		card.setKanji("食べ難い");
		card.setHiragana("たべにくい");
		List<String> results = confuser.getKanjiBoundries(card);
		assertEquals(1, results.size());
		assertEquals("食べ難くい", results.get(0));

		// Test to make sure 青い (blue) is extended correctly
		card = new Card();
		card.setKanji("青い");
		card.setHiragana("あおい");
		results = confuser.getKanjiBoundries(card);
		assertEquals(1, results.size());
		assertEquals("青おい", results.get(0));

		// Test to make sure 話す (to speak) is extended correctly
		card = new Card();
		card.setKanji("話す");
		card.setHiragana("はなす");
		results = confuser.getKanjiBoundries(card);
		assertEquals(1, results.size());
		assertEquals("話なす", results.get(0));

		// Test to make sure お結び (rice ball) is extended correctly
		card = new Card();
		card.setKanji("お結び");
		card.setHiragana("おむすび");
		results = confuser.getKanjiBoundries(card);
		assertEquals(1, results.size());
		assertEquals("お結すび", results.get(0));

		// Test to make sure the する (to do) suffix is not extended
		card = new Card();
		card.setKanji("勉強する");
		card.setHiragana("べんきょうする");
		results = confuser.getKanjiBoundries(card);
		assertEquals(0, results.size());
		
		// Test to make sure 朝御飯 (breakfast) is not extended
		card = new Card();
		card.setKanji("朝御飯");
		card.setHiragana("あさごはん");
		results = confuser.getKanjiBoundries(card);
		assertEquals(0, results.size());
	}
	
	/**
	 * Test to make sure kanji substitution works correctly.
	 */
	@Test
	public void kanjiSubsitutionTest() throws ConfuserException, IOException {
		// TODO Get more unit tests for this function, we need one where the 
		// TODO replacement kanji is in the middle and one were the replacement
		// TODO kanji is at the end
		
		// Test to make sure 介護 (nursing) is manipulated correctly, in this
		// case, the first kanji will be replaced
		String[] expected = new String[] { "价護", "堺護", "界護", "畍護", "疥護", "芥護"};
		checkResults(confuser.getKanjiSubsitution("介護"), expected);
		
		// Test to make sure 党議拘束 (compulsory adherence to a party decision; 
		// restrictions on party debate) is manipulated correctly, in this case
		// the second kanji will be replaced
		expected = new String[] { "党儀拘束", "党嶬拘束", "党犠拘束", "党礒拘束", "党義拘束", "党艤拘束", "党蟻拘束"};
		checkResults(confuser.getKanjiSubsitution("党議拘束"), expected);
		
		// Test to make sure 動詞 (verb) is manipulated correctly, in this case
		// the last kanji will be replaced
		expected = new String[] { "動伺", "動司", "動嗣", "動祠", "動笥", "動覗", "動飼"};
		checkResults(confuser.getKanjiSubsitution("動詞"), expected);
	}

	/**
	 * Test to make sure vowels are elongated correctly for katakana words.
	 */
	@Test
	public void katakanaVowelElongationTest() {
		// Test to make sure コンピュータ (computer) is manipulated correctly
		String[] expected = new String[] { "コーンピュータ", "コンピューター", "コンピュタ" };
		checkResults(confuser.getKatakanaManiuplation("コンピュータ"), expected);
		
		// Test to make sure プロジェクト (project) is being manipulated correctly
		expected = new String[] { "プーロジェクト", "プロージェクト", "プロジェークト", "プロジェクート", "プロジェクトー" };
		checkResults(confuser.getKatakanaManiuplation("プロジェクト"), expected);
		
		// Test to make sure エレベーター (elevator) is manipulated correctly
		expected = new String[] { "エーレベーター", "エレーベーター", "エレベター", "エレベータ" };
		checkResults(confuser.getKatakanaManiuplation("エレベーター"), expected);
	}

	/**
	 * Test to make sure the small tsu character (ッ) is being manipulated
	 * correctly.
	 */
	@Test
	public void smallTsuManipulation() {
		// Test to make sure ヒット (hit) is manipulated correctly
		List<String> results = confuser.getSmallTsuManiuplation("ヒット");
		assertEquals(1, results.size());
		assertEquals("ヒト", results.get(0));
		
		// Test to make sure ヒト  (person) is manipulated correctly
		results = confuser.getSmallTsuManiuplation("ヒト");
		assertEquals(1, results.size());
		assertEquals("ヒット", results.get(0));
	}

	/**
	 * Test to make sure the n character (ん, ン) is being manipulated correctly
	 * for hiragana and katakana.
	 */
	@Test
	public void nManipulation() {
		// Test to make sure テニス (tennis) is manipulated correctly
		List<String> results = confuser.getNManipulation("テニス");
		assertEquals(1, results.size());
		assertEquals("テンニス", results.get(0));
		
		// Test to make sure なのか (seven days) is manipulated correctly
		results = confuser.getNManipulation("なのか");
		assertEquals(1, results.size());
		assertEquals("なんのか", results.get(0));
		
		// Test to make sure こんなん  (stress) is manipulated correctly
		results = confuser.getNManipulation("こんなん");
		assertEquals(1, results.size());
		assertEquals("こなん", results.get(0));
	}
}
