import { Fragment, useEffect } from 'react'
import Signin from '../presentation/components/Signin';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Signup from '../presentation/components/Signup';
import { Home } from '../presentation/pages/Home';
import Header from '../presentation/components/Header';
import Footer from '../presentation/components/Footer';
import UseAuth from '../presentation/hooks/UseAuth';
import { Profile } from '../presentation/pages/Profile';
import { ListProfile } from '../presentation/components/ListProfile';

const RoutesApp = () => {
  const { isAuthenticated } = UseAuth();
  const Private = ({ Item }) => {
    return isAuthenticated ? <Item /> : <Signin />
  }
  useEffect(() => { 
  }, []);
  
  return (    
    <div className='w-full min-h-screen bg-black '>
      <div className="w-full sticky z-20 top-0">
        <Header />
      </div>
      
        <Fragment>
          <Routes>
            <Route path="/home" element={<Private Item={Home} />} />
            <Route path="/" element={<Private Item={Home} />} />
            <Route path="/signup" element={<Signup />} />
            <Route path="*" element={<Signin />} />
            <Route path="/profile" element={<Private Item={Profile} />} />
            <Route path="/list-profile" element={<Private Item={ListProfile} />} />
          </Routes>
        </Fragment>
      
      <Footer />
    </div>
  )
}

export default RoutesApp