import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.Scanner;

public class Polynomial {
	     
	double[] coefficients; 
    int[] exponents; 

	// Constructors
	public Polynomial() {        
		coefficients = new double[]{0.0};
        exponents = new int[0];    
	}

    public Polynomial(double[] coefficients, int[] exponents) {        
		this.coefficients = coefficients; 
        this.exponents = exponents;   
	}


	// Methods
	public Polynomial add(Polynomial other) {
        int maximum_length = Math.max(this.coefficients.length, other.coefficients.length);
        double[] result = new double[maximum_length];

        for(int i = 0; i < this.coefficients.length; i++) {
            result[i] += this.coefficients[i];
        }

        for(int i = 0; i < other.coefficients.length; i++) {
            result[i] += other.coefficients[i];
        }

        return new Polynomial(result);
    }

	public double evaluate(double x) {
        double result = 0.0;

        for(int i = 0; i < coefficients.length; i++) {
            result += coefficients[i] * Math.pow(x, exponents[i]);
        }
        return result;
    }

	public boolean hasRoot(double x) {
        return evaluate(x) == 0.0;
    }
    
    public Polynomial multiply(Polynomial other) {

    if (this.exponents.length == 0)
      return new Polynomial(other.coefficients, other.exponents);

    if (other.exponents.length == 0)
      return new Polynomial(this.coefficients, this.exponents);

    int max_exponent = Math.max(this.exponents[this.exponents.length - 1],
            other.exponents[other.exponents.length - 1]);

    double[] temp_coefficients = new double[max_exponent + 1];

    for (int i = 0; i < this.coefficients.length; i++)
      temp_coefficients[this.exponents[i]] = this.coefficients[i];

    for (int i = 0; i < other.coefficients.length; i++) {
      if (temp_coefficients[other.exponents[i]] == 0)
        temp_coefficients[other.exponents[i]] = other.coefficients[i];
      else
        temp_coefficients[other.exponents[i]] *= other.coefficients[i];
    }

    int length = 0;
    for (int i = 0; i < temp_coefficients.length; i++) {
      if (temp_coefficients[i] != 0) {
        length++;
      }
    }

    double[] new_coefficients = new double[len];
    int[] new_exponents = new int[len];

    int j = 0;
    for (int i = 0; i < temp_coefficients.length; i++) {
      if (temp_coefficients[i] != 0) {
        new_coefficients[j] = temp_coefficients[i];
        new_exponents[j] = i;
        j += 1;
      }
    }
    return new Polynomial(new_coefficients, new_exponents);
  }

    public Polynomial(File file) throws Exception {

    Scanner scanner = new Scanner(file);
    String line = scanner.nextLine();
    scanner.close();


    line = line.replace("-", "+-");
    String[] terms = line.split("\\+");

    this.coefficients = new double[terms.length];
    this.exponents = new int[terms.length];

    for (int i = 0; i < terms.length; i++) {
      String[] temp = terms[i].split("x");
      this.coefficients[i] = Double.parseDouble(temp[0]);
      this.exponents[i] = (temp.length == 1) ? 0 : Integer.parseInt(temp[1]);
    }
  }

  public void saveToFile(String filename) throws Exception {
    FileWriter writer = new FileWriter(filename);
    String line = "";

    for (int i = 0; i < this.coefficients.length; i++) {
      line += this.coefficients[i] + "x" + this.exponents[i] + "+";
    }
  }
}