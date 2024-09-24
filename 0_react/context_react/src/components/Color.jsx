import { useContext } from "react";
import { MyColorContext } from "../contexts/MyColorContext";
       
const Color = () => {
    const { myColor } = useContext(MyColorContext);  
    return (
        // 여기서 가로 128px, 세로 128px인 박스를 만들고, myColor 색상으로 배경을 만들어라
        <div style={{
            width: "128px",
            height: "128px",
            backgroundColor: myColor,
            margin: '24px auto'
        }}></div>
    );
}
 
export default Color;