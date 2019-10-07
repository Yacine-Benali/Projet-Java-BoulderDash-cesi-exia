package exia.BoulderDash.helpers;

import exia.BoulderDash.exceptions.UnknownModelException;
import exia.BoulderDash.models.BoulderModel;
import exia.BoulderDash.models.BrickWallModel;
import exia.BoulderDash.models.DiamondModel;
import exia.BoulderDash.models.DirtModel;
import exia.BoulderDash.models.DisplayableElementModel;
import exia.BoulderDash.models.EmptyModel;
import exia.BoulderDash.models.RockfordModel;
import exia.BoulderDash.models.SteelWallModel;


/**
 * ModelConvertHelper
 *
 * Provides model conversion services.
 *
 */
public class ModelConvertHelper {
    /**
     * Class constructor
     */
    public ModelConvertHelper() {
        // Nothing done.
    }

    /**
     * Gets the model associated to the string
     *
     * @param   spriteName  Sprite name
     * @return  Model associated to given sprite name
     */
    public DisplayableElementModel toModel(String spriteName, boolean isConvertible) throws UnknownModelException {
        DisplayableElementModel element;

        // Instanciates the sprite element matching the given sprite name
        switch (spriteName) {
            case "black":
            case "Black":
                element = new EmptyModel();
                break;

            case "boulder":
            case "Boulder":
                element = new BoulderModel(isConvertible);
                break;

            case "brickwall":
            case "Brick Wall":
                element = new BrickWallModel();
                break;

            case "diamond":
            case "Diamond":
                element = new DiamondModel();
                break;

            case "dirt":
            case "Dirt":
                element = new DirtModel();
                break;

      

            case "rockford":
            case "Rockford":
                element = new RockfordModel();
                break;

            case "steelwall":
            case "Steel Wall":
                element = new SteelWallModel();
                break;

         

            default:
                throw new UnknownModelException("Unknown model element > " + spriteName);
        }

        return element;
    }

 
}
