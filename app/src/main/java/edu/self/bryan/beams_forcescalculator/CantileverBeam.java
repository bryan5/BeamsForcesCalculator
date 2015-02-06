package edu.self.bryan.beams_forcescalculator;

public class CantileverBeam {

    public double elasticity = 0;
    public double inertia = 0;

    public double lengthTotal = 0;
    public double force = 0;
    public double partialLength = 0;
    public double uniformLoad = 0;

    public double reaction;
    public double maxShear;
    public double maxMoment;
    public double maxDeflection;

    public void calculateReaction(int id) {
        switch (id) {
            case 0: reaction = force;
                    break;
            case 1: reaction = force;
                    break;
            case 2: reaction = uniformLoad * lengthTotal;
                    break;
        }
    }

    public void calculateMaxShear(int id) {
        switch (id) {
            case 0: maxShear = force;
                    break;
            case 1: maxShear = force;
                    break;
            case 2: maxShear = uniformLoad * lengthTotal;
                    break;
        }
    }

    // A moment can have a negative value depending on the circumstances
    public void calculateMaxMoment(int id) {
        switch (id) {
            case 0: maxMoment = force * (-lengthTotal);
                    break;
            case 1: maxMoment = force * partialLength;
                    break;
            case 2: maxMoment = (uniformLoad * Math.pow(lengthTotal, 2)) / 2;
                    break;
        }
    }

    // Force is applied downward, therefore the deflection is a negative value
    public void calculateMaxDeflection(int id) {
        switch (id) {
            case 0: maxDeflection = -(force * Math.pow(lengthTotal, 3)) / (3 * elasticity * inertia);
                    break;
            case 1: maxDeflection = ((force * Math.pow(partialLength, 2)) / (6 * elasticity * inertia))
                                    * (partialLength - (3 * lengthTotal));
                    break;
            case 2: maxDeflection = -(uniformLoad * Math.pow(lengthTotal, 4)) / (8 * elasticity * inertia);
                    break;
         }
    }

}
