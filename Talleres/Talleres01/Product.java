public class Product {

    private String name;
    private double price;
    private int stock;

    //Crea un producto con el nombre precio y stock
    //Si algun valor es invalido se usa un valor predeterminado
    // @param name  para el nombre del producto
    // @param price el precio que debe ser mayor que 0
    // @param stock el stock que no debe ser negativo
     
    public Product(String name, double price, int stock) {
        this.name = isValidName(name) ? name : "Unnamed";
        this.price = isValidPrice(price) ? price : 1.0;
        this.stock = isValidStock(stock) ? stock : 0;
    }

    //devuelve el nombre del producto
    public String getName() {
        return name;
    }

    //devuelve el precio del
    public double getPrice() {
        return price;
    }

    //devuelve el stock available
    public int getStock() {
        return stock;
    }

    //intenta actualizar el nombre del producto siempre y cuando no sea vacio el nuevo
    //@param newName el nombre del candidato
    //@return true si el nombre fue aceptado y actualizado, false de cualquier otra forma
     
    public boolean updateName(String newName) {
        if (isValidName(newName)) {
            this.name = newName;
            return true;
        }
        return false;
    }

    //intenta actualizar el precio del producto siempre y cuando sea mayor que cero
    //@param newPrice el precio del candidato
    //@return true si el precio fue aceptado y actualizado, false de cualquier otra forma
    public boolean updatePrice(double newPrice) {
        if (isValidPrice(newPrice)) {
            this.price = newPrice;
            return true;
        }
        return false;
    }

    //intenta actualizar available stock, se aplica siempre y cuando no sea negativo
    //@param newStock el nuevo stock
    //@return true si el stock fue aceptado y actualizado, false de cualquier otra forma
    public boolean updateStock(int newStock) {
        if (isValidStock(newStock)) {
            this.stock = newStock;
            return true;
        }
        return false;
    }

    private boolean isValidName(String candidateName) {
        return candidateName != null && !candidateName.trim().isEmpty();
    }

    private boolean isValidPrice(double candidatePrice) {
        return candidatePrice > 0;
    }

    private boolean isValidStock(int candidateStock) {
        return candidateStock >= 0;
    }
}