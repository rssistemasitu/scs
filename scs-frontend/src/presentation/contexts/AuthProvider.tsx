import React, { createContext, useContext, useEffect, useState } from 'react';
import Cookies from 'js-cookie';
import { UserInfo, signin, signout } from '../api/Api';

interface AuthContextData {
  isAuthenticated: boolean;
  login: (email: string, password: string) => void;
  logout: () => void;
  user: {}
}

export const AuthContext = createContext<AuthContextData>({
  isAuthenticated: false,
  login: () => {},
  logout: () => {},
  user: {}
});

export const useAuth = (): AuthContextData => useContext(AuthContext);

export const AuthProvider: React.FC<React.PropsWithChildren<{}>> = ({ children }) => {
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [user, setUser] = useState<UserInfo>();

  useEffect(() => {    
    const userLogged = Cookies.get("user")  ?? false;        
    if (userLogged) {
      const userParsed = JSON.parse(userLogged);
      setUser(userParsed);
      setIsAuthenticated(true);
    }     
  }, []);

  const login = (email: string, password: string) => {
    signin(email, password)
    .then((user: User) => {
      if(user.token) {
        Cookies.set('user', JSON.stringify({ user }), { secure: true, expires: 7 });
        setIsAuthenticated(true)
      }
    });
  };

  const logout = (token: string) => {
    signout(token)
      .then(() => {
        setIsAuthenticated(false);
        Cookies.remove('user')
    });
  };  

  const authContextData: AuthContextData = {
    isAuthenticated,
    login,
    logout,
    user
  };

  
  return (
    <AuthContext.Provider
      value={{
        isAuthenticated,
        login,
        logout,
        user
        }}>
      {children}
    </AuthContext.Provider>
  );
};
