import { useContext } from "react";
import { SamsungContext } from "../contexts/SamsungContext";

const GrandChild = () => {
    const samsung = useContext(SamsungContext);
    return (
        <>
            <h5>손주 - {samsung}</h5>
        </>
    );
}
 
export default GrandChild;