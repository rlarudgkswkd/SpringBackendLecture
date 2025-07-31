package org.example.Head03_oopProgramming.topic06_Enum.example02;

public class PlanetTest{
    public enum Planet {
        MERCURY(3.303e+23, 2.4397e6,"수성"),
        VENUS(4.869e+24, 6.0518e6,"금성"),
        EARTH(5.976e+24, 6.37814e6, "지구");

        private final double mass;
        private final double radius;
        private final String planetName;

        Planet(double mass, double radius, String planetName) {
            this.mass = mass;
            this.radius = radius;
            this.planetName = planetName;
        }

        public double surfaceGravity() {
            final double G = 6.67300E-11;
            return G * mass / (radius * radius);
        }

        @Override
        public String toString() {
            return name() + " (gravity=" + surfaceGravity() + ", 행성이름:" + planetName + ")";
        }
    }

    public static void main(String[] args) {
        for (Planet planet : Planet.values()) {
            System.out.println("Planet: " + planet.toString());
            System.out.println("surfaceGravity: " + planet.surfaceGravity());
        }
    }
}