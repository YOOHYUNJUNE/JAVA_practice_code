import axios from "axios";
import { useState } from "react";
import styled from 'styled-components';

const ProdBox = ({ prod, dispatch }) => {
    const [isUpdateMode, setIsUpdateMode] = useState(false);
    const [product, setProduct] = useState({name: prod.name, price: prod.price});

    const handleChange = (e) => {
        setProduct(prevState => ({...prevState, [e.target.name]: e.target.value}))
    }

    const handleUpdateMode = () => {
        setIsUpdateMode(true);
    }

    const handleUpdate = async() => {
        try {
            // product <- 입력한 상품명과 가격
            // 서버에 업데이트 요청
            // put : 전체에 대한 변경
            // patch : 일부에 대한 변경
            const editProduct = {...product, id: prod.id}
            const res = await axios.put(`http://localhost:8080/products/${prod.id}`, editProduct);
            dispatch({type: "EDIT_PRODUCT", payload: editProduct});
            // const updateProducts = products.map(p => (p.id === prod.id ? editProduct : p));
            // setProducts(updateProducts);
            setIsUpdateMode(false);
        } catch (error) {
            console.error(error);
        }
        
    }

    const handleDelete = async() => {
        try {
            const res = await axios.delete(`http://localhost:8080/products/${prod.id}`);
            dispatch({type: "DELETE_PRODUCT", payload: prod.id});
            // const newProducts = products.filter(p => {
            //     return p.id != prod.id
            // });
            // setProducts(newProducts);
        } catch (error) {
            console.error(error);
        }
    }
    
    return ( 
        <div style={{
            textAlign: 'left',
            display: 'flex',
            flexDirection: 'column',
            width: '30%',
            padding: '10px'
        }}>
            <div style={{
                width: '100%',
                height: '100px',
                backgroundColor: 'lightgray'
            }}>
                {
                    isUpdateMode ?
                        <>
                            <div><input type="text" name="name" value={product.name} onChange={handleChange} /></div>
                            <div><input type="number" name="price" value={product.price} onChange={handleChange} /></div>
                            <ProdBtn onClick={handleUpdate}>완료</ProdBtn>
                        </>
                        :
                        <>
                            <div>
                                <a href={"/product/" + prod.id}>{prod.name}</a>
                            </div>
                            <div>{prod.price}</div>
                            <ProdBtn primary onClick={handleUpdateMode}>수정</ProdBtn>
                            <ProdBtn danger onClick={handleDelete}>삭제</ProdBtn>
                        </>
                }
            </div>
        </div>
     );
}

const ProdBtn = styled.button`
    background-color: ${props =>
        props.primary ? "blue" :
        props.danger ? "red" 
        : "#fff" 
    };
    font-size: 1em;
    padding: 0.25em 1em;
    border: 2px solid #BF4F74;
    border-radius: 3px;
`

export default ProdBox;