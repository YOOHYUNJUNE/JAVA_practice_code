import axios from "axios";
import { useState } from "react";

const ProdInput = ({dispatch}) => {
    const [newProduct, setNewProduct] = useState({
        name: "",
        description: "",
        price: 0
    });

    const handleChange = (e) => {
        // const name = e.target.name;
        // const value = e.target.value;
        // setNewProduct({...newProduct, [name] : value})

        // const {name, value} = e.target;
        // setNewProduct({...newProduct, [name] : value})

        // setNewProduct({...newProduct, [e.target.name] : e.target.value})
        setNewProduct(prevState => ({...prevState, [e.target.name]: e.target.value}))
    }
    
    const handleAddProd = async() => {
        try {
            const res = await axios.post('http://localhost:8080/products', newProduct);
            const newProd = res.data;
            dispatch({type: "ADD_PRODUCT", payload: newProd});
        } catch (error) {
            console.error(error);
        }
    }

    return ( 
        <div>
            <input type='text' name="name" value={newProduct.name} onChange={handleChange} />
            <input type='text' name="description" value={newProduct.description} onChange={handleChange} />
            <input type='number' name="price" min="0" step="1000" value={newProduct.price} onChange={handleChange} />
            <button onClick={handleAddProd}>추가</button>
        </div>
     );
}
 
export default ProdInput;