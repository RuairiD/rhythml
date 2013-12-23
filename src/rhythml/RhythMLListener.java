package rhythml;

// Generated from RhythML.g4 by ANTLR 4.1
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link RhythMLParser}.
 */
public interface RhythMLListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link RhythMLParser#beats}.
	 * @param ctx the parse tree
	 */
	void enterBeats(@NotNull RhythMLParser.BeatsContext ctx);
	/**
	 * Exit a parse tree produced by {@link RhythMLParser#beats}.
	 * @param ctx the parse tree
	 */
	void exitBeats(@NotNull RhythMLParser.BeatsContext ctx);

	/**
	 * Enter a parse tree produced by {@link RhythMLParser#p}.
	 * @param ctx the parse tree
	 */
	void enterP(@NotNull RhythMLParser.PContext ctx);
	/**
	 * Exit a parse tree produced by {@link RhythMLParser#p}.
	 * @param ctx the parse tree
	 */
	void exitP(@NotNull RhythMLParser.PContext ctx);

	/**
	 * Enter a parse tree produced by {@link RhythMLParser#rhy}.
	 * @param ctx the parse tree
	 */
	void enterRhy(@NotNull RhythMLParser.RhyContext ctx);
	/**
	 * Exit a parse tree produced by {@link RhythMLParser#rhy}.
	 * @param ctx the parse tree
	 */
	void exitRhy(@NotNull RhythMLParser.RhyContext ctx);

	/**
	 * Enter a parse tree produced by {@link RhythMLParser#bar}.
	 * @param ctx the parse tree
	 */
	void enterBar(@NotNull RhythMLParser.BarContext ctx);
	/**
	 * Exit a parse tree produced by {@link RhythMLParser#bar}.
	 * @param ctx the parse tree
	 */
	void exitBar(@NotNull RhythMLParser.BarContext ctx);

	/**
	 * Enter a parse tree produced by {@link RhythMLParser#inst}.
	 * @param ctx the parse tree
	 */
	void enterInst(@NotNull RhythMLParser.InstContext ctx);
	/**
	 * Exit a parse tree produced by {@link RhythMLParser#inst}.
	 * @param ctx the parse tree
	 */
	void exitInst(@NotNull RhythMLParser.InstContext ctx);
}