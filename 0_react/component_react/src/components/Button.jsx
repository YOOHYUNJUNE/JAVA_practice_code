const Button = ({size, color, event, children}) => {
    const buttonSize = {
        small: {w: "50px", h: "25px", font: '0.5em'},
        medium: {w: "75px", h: "38px", font: '0.8em'},
        large: {w: "100px", h: "50px", font: '1.5em'}
    }
    const buttonStyle = {
        width: buttonSize[size].w,
        height: buttonSize[size].h,
        backgroundColor: color,
        fontSize: buttonSize[size].font,
        fontWeight: "bold",
        borderRadius: "10px",
        border: "none",
        outline: "none",
        cursor: "pointer",
        boxShadow: "5px 5px 5px rgba(0, 0, 0, 0.3)"
    }
    return ( 
        <button style={buttonStyle} onClick={event}>{children}</button>
     );
}

Button.defaultProps = {
    size: "medium",
    color: "gray",
    children: "클릭"
}
 
export default Button;