/**
 * Class: COP 3330 OBJECT ORIENTED PROGRAMMING (Java) 12:30PM - 1:20PM
 * Homework FOUR-E : AdventureGame Extra credit
 * CaveWorker: Modifier for an open location and non-open location.
 *
 * @author Justin Holzman
 */
public interface CaveWorker {
        /** Method making modifications to a given cave if this worker can modify this type of cave. @param loc*/
        boolean modifyCave(Cave loc);
        
        /** Give a description of the type of modification made. */
        String describeModification();
}