import { AppBar, Box, Button, Toolbar, Typography } from "@mui/material";
import { Link } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

const Navbar = () => {
  const { isAuthenticated, handleLogout } = useAuth();

  return (
    <AppBar position="static">
      <Toolbar>
        <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
          My Chat App
        </Typography>

        <Box sx={{ display: "flex", alignItems: "center" }}>
          <Link to="/" style={{ textDecoration: "none", marginRight: "1rem" }}>
            <Button sx={{ color: "#FFFFFF" }}>Home</Button>{" "}
          </Link>

          {isAuthenticated ? (
            <Button sx={{ color: "#FFFFFF" }} onClick={handleLogout}>
              Logout
            </Button>
          ) : (
            <Link to="/login" style={{ textDecoration: "none" }}>
              <Button sx={{ color: "#FFFFFF" }}>Login</Button>
            </Link>
          )}
        </Box>
      </Toolbar>
    </AppBar>
  );
};

export default Navbar;
