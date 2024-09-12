import { jwtDecode } from "jwt-decode";
import { useCookies } from "react-cookie"
import { Navigate } from 'react-router-dom';

export const LoginUserRoute = ({ comp: Comp }) => {
    const [ cookies ] = useCookies(["accessToken"]);
    const token = cookies.accessToken;

    if(!token) {
        return <Navigate to="/" />
    }

    const decodeToken = jwtDecode(token);
    if (decodeToken.exp * 1000 < Date.now()) {
        return <Navigate to="/" />
    }

    return <Comp />
}

export const AdminRoute = ({ comp: Comp }) => {
    const [ cookies ] = useCookies(["accessToken"]);
    const token = cookies.accessToken;

    if(!token) {
        return <Navigate to="/" />
    }

    const decodeToken = jwtDecode(token);
    if (decodeToken.exp * 1000 < Date.now() || decodeToken.role !== "ROLE_ADMIN") {
        return <Navigate to="/" />
    }
    
    return <Comp />
}
