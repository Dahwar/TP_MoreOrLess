package france.uha.ensisa.fl.moreorless;

import javafx.animation.FadeTransition;
import javafx.animation.FadeTransitionBuilder;
import javafx.animation.TranslateTransition;
import javafx.animation.TranslateTransitionBuilder;
import javafx.scene.Node;
import javafx.util.Duration;

/**
 *
 * @author Florent
 */
public class TransitionFactory {

    public TransitionFactory() {
    }
    
    public FadeTransition getFadeTransition(double duration, Node node, double fromValue, double toValue, int cycleCount, boolean autoReverse) {
        return FadeTransitionBuilder.create()
            .duration(Duration.seconds(duration))
            .node(node)
            .fromValue(fromValue)
            .toValue(toValue)
            .cycleCount(cycleCount)
            .autoReverse(autoReverse)
            .build();
    }
    
    public TranslateTransition getTranslateTransitionX(double duration, Node node, double fromX, double toX, int cycleCount, boolean autoReverse) {
        return TranslateTransitionBuilder.create()
            .duration(Duration.seconds(duration))
            .node(node)
            .fromX(fromX)
            .toX(toX)
            .cycleCount(cycleCount)
            .autoReverse(autoReverse)
            .build();
    }
}
