import styled from "styled-components";

const ProductBox = ({ product }) => {
    return (
        <StyledProductBox>
            <div>
                <p>{product.name}</p>
                <p>{product.price}</p>
            </div>
        </StyledProductBox>
    );
}

const StyledProductBox = styled.div`
    display: flex;
    flex-direction: column;
    width: 30%;
    padding: 10px;
`

export default ProductBox;