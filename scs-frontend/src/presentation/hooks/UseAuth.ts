import { useContext } from "react";
import { AuthContext } from "../contexts/AuthProvider";

const UseAuth = () => {
  const context = useContext(AuthContext);
  return context;
};

export default UseAuth;