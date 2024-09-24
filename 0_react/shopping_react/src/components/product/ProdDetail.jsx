import axios from "axios";
import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";

const ProdDetail = () => {
    let { id } = useParams();
    const [prodDetail, setProdDetail] = useState();
    const [isLoading, setIsLoading] = useState(true);

    const getProdDetail = async() => {
        try {
            const res = await axios.get(`http://localhost:8080/products/${id}`);
            console.log(res.data);
            setProdDetail(res.data);
            setIsLoading(false);
        } catch (error) {
            console.error(error);
        }
    }

    useEffect(() => {
        getProdDetail();
    }, []);

    if (isLoading) {
        return <>로딩 중...</>
    }
    return (
        <>
            <div>아이디 : {prodDetail.id}</div>
            <div>상품명 : {prodDetail.name}</div>
            <div>상품설명 : {prodDetail.description}</div>
            <div>가격 : {prodDetail.price}</div>
        </>
    );
}
 
export default ProdDetail;