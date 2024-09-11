// 접근 제어용 컴포넌트

import { useAuth } from "../../hooks/useAuth";
import NotFound from "./NotFound";

const AccessControl = ({children, roleList}) => {

    const {userInfo} = useAuth();

    const role = userInfo?.role || 'none';

    console.log("유저 권한 : ", role);  
    
    if (roleList.includes(role)) {
        return children;
    } else {
        return <NotFound/>
    }

    


}
 
export default AccessControl;