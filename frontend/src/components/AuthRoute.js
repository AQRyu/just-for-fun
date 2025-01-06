import { Navigate, Outlet } from "react-router-dom";

const AuthRoute = () => {
  const isAuthenticated = localStorage.getItem("user");

  return isAuthenticated ? <Outlet /> : <Navigate to="/login" />;
};

export default AuthRoute;
