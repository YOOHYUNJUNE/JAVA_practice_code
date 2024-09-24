import { useContext } from "react";
import { MyColorContext } from "../contexts/MyColorContext";

const ColorSelector = () => {
    const rainbow = [
        'red', 'orange', 'yellow', 'green', 'blue', 'navy', 'purple'
    ]

    const { myColor, setMyColor} = useContext(MyColorContext);

    const handleColor = (color) => {
        setMyColor(color)
    }
    return (
        <div>
            <h1>색상을 선택하세요</h1>
            <div style={{display: 'flex', justifyContent: 'center'}}>
                {
                    rainbow.map(color => (
                        <div
                            key={color}
                            style={{
                                width: "36px",
                                height: "36px",
                                cursor: "pointer",
                                backgroundColor: color,
                                border: myColor == color && '10px solid black'
                            }}
                            onClick={() => handleColor(color)}
                        >
                        </div>
                    ))
                }
            </div>
        </div>
    );
}
 
export default ColorSelector;