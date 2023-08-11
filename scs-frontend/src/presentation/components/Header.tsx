import { Link } from 'react-router-dom';
import logo from '../../assets/logo_scs_bg_cut.png'
import foto from '../../assets/rog.jpg'
import { ArrowRightOnRectangleIcon, Bars4Icon, BellIcon, GiftIcon, XMarkIcon } from '@heroicons/react/24/solid';
import { useEffect, useState } from 'react';
import UseAuth from '../hooks/UseAuth';
import io from 'socket.io-client';
import Modal from './Modal';

export type Notification = {
  to: string;
  from: string;
  content: string;
  timestamp: string;
}

const socket = io('http://localhost:8878');

const Header = () => {
  const [menuActive, setMenuActive] = useState(false)
  const { logout, isAuthenticated, user } = UseAuth();
  const [message, setMessage] = useState<string>("");
  const [openModal, setOpenModal] = useState(false);

  const menuToogle = () => {
    let menu = document.getElementById('menu');
    menu?.classList.toggle('activeMenu')
    menu?.classList.contains('activeMenu') ? setMenuActive(true) : setMenuActive(false)
  }   
  
  
  const [on, setOn] = useState(false);

  useEffect(() => {

    const closeModal = () => {
      setOpenModal(false)
    }

    socket.on('connect', () => {
      console.log('Conectado ao servidor! '+ socket.id);
      setOn(true);  

      socket.onAny((messageSendToAllUsers, ...args: Notification[])=>{
        setMessage(args[0].content);
        setOpenModal(true)
        setTimeout(closeModal, 10000)
      });       

    }); 

    return () => {
      socket.off();
    };

  }, [on])


 const sendMessage = () => {
    socket.emit("messageSendToAllUsers", { 
      to: "all",
      from: socket.id,
      content: "Mensagem do Frontend",
      timestamp: new Date().toLocaleDateString
    }, 
    console.log("Mensagem enviada..."));
 } 

      
  return (
    <div className="w-screen p-3 fixed bg-black">
      <Modal props={openModal} text={message}/>
      <div className="max-w-7x1 flex  mx-auto">
        {/* Logo brand */}
        <img className='w-[3rem]' src={logo} alt="" />

        {/* Menu Btn */}
        {!menuActive ? <Bars4Icon className='md:hidden flex w-7  transition-all .2s text-gray-300 float-right absolute right-5' onClick={menuToogle}/> :
        <XMarkIcon className='md:hidden flex w-7 animate-[spin_1s] transition-all .2s text-gray-300 float-right absolute right-5' onClick={menuToogle}/>}

                
        {/* Menu */}
        <div id="menu" className="menu z-100  md:m-0 m-8 bg-black flex md:flex-row flex-col md:justify-between md:items-center">

          {/* Left header links */}
          <div className="md:m-0 m-8">

            {/* Links */}
            <div className="links md:ml-8">
              <ul className="flex md:flex-row flex-col gap-3 text-gray-400 md:items-center">              
                <li className='mx-1 hover:text-red-400 font-semibold text-red-500 cursor-pointer'><Link to="/home">Home</Link></li>
                <li className='mx-1 hover:text-gray-500 cursor-pointer'>Recomendações</li>
                <li className='mx-1 hover:text-gray-500 cursor-pointer'>Filmes</li>
                <li className='mx-1 hover:text-gray-500 cursor-pointer'>Séries</li>
                <li className='mx-1 hover:text-gray-500 cursor-pointer'>Recém adicionados</li>
                <li className='mx-1 hover:text-gray-500 cursor-pointer'>Animação</li>
                <li className='mx-1 hover:text-gray-500 cursor-pointer'>Minha Lista</li>
              </ul>
            </div>
          </div>

          {/* Rigth header links */}
          <div className="flex md:ml-0 ml-7 gap-3 md:flex-row flex-col text-gray-300 md:items-center">
            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" className="w-5 mx-2 cursor-pointer">
              <path strokeLinecap="round" strokeLinejoin="round" d="M21 21l-5.197-5.197m0 0A7.5 7.5 0 105.196 5.196a7.5 7.5 0 0010.607 10.607z" />
            </svg>
            <GiftIcon className='w-5 mx-2 cursor-pointer' />
            <BellIcon className='w-5 mx-2 cursor-pointer' />
            {isAuthenticated ? <Link to="/list-profile"><img className='w-8 mx-2 h-8 rounded-full cursor-pointer' src={foto} alt="" /></Link>: null}
            {isAuthenticated ? <ArrowRightOnRectangleIcon className='w-5 mx-2 cursor-pointer' type='button' onClick={() => logout(user.user.token)}/>
            : null}     
          </div>

        </div>

      </div>
    </div>

  )
}

export default Header