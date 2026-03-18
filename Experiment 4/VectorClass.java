public class VectorClass
{
    double[] comps;
    public VectorClass(double[] arr) throws InvalidVectorException
    {
        if(arr == null || arr.length == 0)
            throw new InvalidVectorException("Invalid Vector");
        if(arr.length != 2 && arr.length != 3)
            throw new InvalidVectorException("Only 2D or 3D supported");
        comps = arr.clone();
    }
    public VectorClass add(VectorClass v) throws InvalidVectorException
    {
        checkDimension(v);
        double results[] = new double[comps.length];
        for(int i = 0; i < comps.length; i++)
            results[i] = comps[i] + v.comps[i];
        return new VectorClass(results);
    }
    public VectorClass subtract(VectorClass v) throws InvalidVectorException
    {
        checkDimension(v);
        double results[] = new double[comps.length];
        for(int i = 0; i < comps.length; i++)
            results[i] = comps[i] - v.comps[i];
        return new VectorClass(results);
    }
    public double dotProduct(VectorClass v) throws InvalidVectorException
    {
        checkDimension(v);
        double result = 0;
        for(int i = 0; i < comps.length; i++)
            result += comps[i] * v.comps[i];
        return result;
    }
    void checkDimension(VectorClass v) throws InvalidVectorException
    {
        if(comps.length != v.comps.length)
            throw new InvalidVectorException("Incompatible Vector Dimension. Operation cannot be done");
    }
    public void print()
    {
        if(comps.length == 2)
            System.out.println("Vector(" + comps[0] + ", " + comps[1] + ")");
        else
            System.out.println("Vector(" + comps[0] + ", " + comps[1] + ", " + comps[2] + ")");
    }
}