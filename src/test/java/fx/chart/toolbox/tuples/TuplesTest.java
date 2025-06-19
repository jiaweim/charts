package fx.chart.toolbox.tuples;

import org.junit.jupiter.api.Test;


public class TuplesTest {

    @Test
    void testTuples() {
        System.out.println("\n-------------------- tuples test --------------------");
        fx.chart.toolbox.tuples.Pair<Double, Integer> pair = new Pair(5.0, 3);
        assert pair.getA() == 5.0;
        assert pair.getB() == 3;

        fx.chart.toolbox.tuples.Triplet<Double, Integer, Long> triplet = new Triplet(5.0, 3, 500L);
        assert triplet.getTypeAt(2).equals(Long.class);
        assert triplet.getC() == 500;


        fx.chart.toolbox.tuples.Quartet<Double, Integer, String, Long> quartet = new Quartet(1.0, 5, "Test", 1000);
        assert quartet.size() == 4;
        assert quartet.getB() == 5;
        assert quartet.getTypeAt(1).equals(Integer.class);
        assert quartet.getValueAt(2).equals("Test");
    }
}
