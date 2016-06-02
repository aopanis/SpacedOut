package com.jae.spacedout.utility;

public class Utils
{
    /** Maps a number from one range to another
     * @param inStart low value of input range
     * @param inEnd high value of input range
     * @param outStart low value of output range
     * @param outEnd high value of output range
     * @param inValue value in terms of input range
     * @return value in terms of output range
     */
    public static float mapRange(float inStart, float inEnd, float outStart, float outEnd, float inValue)
    {
        return outStart + ((inValue - inStart) * (outEnd - outStart)) / (inEnd - inStart);
    }

    /** Returns an angle from 0 to 360
     * @param degrees input angle in degrees
     * @return normalized angle
     */
    public static float normalizeAngle(float degrees)
    {
        degrees %= 360;
        degrees = (degrees + 360f) % 360;
        return degrees;
    }

    /** Finds the squared distance between two points
     * @param x1 the first point's x coordinate
     * @param y1 the first point's y coordinate
     * @param x2 the second point's x coordinate
     * @param y2 the second point's y coordinate
     * @return the squared distance
     */
    public static float findDistanceSquared(float x1, float y1, float x2, float y2)
    {
        return (x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1);
    }

    /** Finds the length squared of a vector
     * @param x the x length
     * @param y the y length
     * @return the squared distance
     */
    public static float findLengthSquared(float x, float y)
    {
        return x * x + y * y;
    }
}
