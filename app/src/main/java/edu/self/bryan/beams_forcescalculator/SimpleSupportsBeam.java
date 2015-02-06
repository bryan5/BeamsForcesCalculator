package edu.self.bryan.beams_forcescalculator;

public class SimpleSupportsBeam {

    public double elasticity = 0;
    public double inertia = 0;

    public double lengthTotal = 0;
    public double force = 0;
    public double uniformLoad = 0;

    public double reaction;
    public double maxShear;
    public double maxMoment;
    public double maxDeflection;

    public void calculateReactions(int id) {
        switch (id) {
            case 3: reaction = force / 2;
                    break;
            case 4: reaction = (uniformLoad * lengthTotal) / 2;
                    break;
        }
    }

    public void calculateMaxShear(int id) {
        switch (id) {
            case 3: maxShear = force / 2;
                    break;
            case 4: maxShear = (uniformLoad * lengthTotal) / 2;
                    break;
        }
    }

    public void calculateMaxMoment(int id) {
        switch (id) {
            case 3: maxMoment = (force * lengthTotal) / 4;
                    break;
            case 4: maxMoment = (uniformLoad * Math.pow(lengthTotal, 2)) / 8;
                    break;
        }
    }

    // Deflection in these cases is a negative value because the force is applied downwards
    public void calculateMaxDeflection(int id) {
        switch (id) {
            case 3: maxDeflection = -(force * Math.pow(lengthTotal, 3)) / (48 * elasticity * inertia);
                    break;
            case 4: maxDeflection = -(5 * uniformLoad * Math.pow(lengthTotal, 4)) / (384 * elasticity * inertia);
                    break;
        }
    }

}
