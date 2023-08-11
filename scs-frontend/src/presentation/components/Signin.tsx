import React, { useEffect, useState } from 'react'
import { Link, useNavigate } from 'react-router-dom';
import UseAuth from '../hooks/UseAuth';
import io from 'socket.io-client';
import Modal from './Modal';

const initState = () => {
  return {
    email: '',
    password: '',
  }
};

const Signin = () => {  
  const [values, setValues] = useState(initState);
  const [error, setErrors] = useState("");
  const { login } = UseAuth();
  const navigate = useNavigate();
  
  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setValues(prevState => ({
      ...prevState,
      [name]: value,
    }));
  };

  const handleSubmit = (e: React.FormEvent) => {
    setErrors("");
    e.preventDefault();

    if(!values) {
      setErrors("Preencha todos os campos");
      console.log(error);
      return;
    }

    const res = login(values.email, values.password);

    if(res) {
      setErrors(res)
      return
    }
    navigate("/home")
  };

  return (
    <div className="w-full min-h:[80vh] bg-black">
      <div className="h-full">
        
      <div className="flex min-h-full flex-1 flex-col justify-center px-6 py-12 lg:px-8">
        <div className="sm:mx-auto sm:w-full sm:max-w-sm">
          <h2 className="mt-4 text-center text-2xl font-bold leading-9 tracking-tight text-white">
            Acessar minha conta
          </h2>
        </div>

        <div className="mt-5 sm:mx-auto sm:w-full sm:max-w-sm">
          <form className="space-y-6" action="#" method="POST"  onSubmit={handleSubmit}>
            <div>
              <label htmlFor="email" className="block text-sm font-medium leading-6 text-gray-300">
                E-mail
              </label>
              <div className="mt-2">
                <input
                  value={values.email}
                  onChange={handleChange}
                  id="email"
                  name="email"
                  type="email"
                  autoComplete="false"
                  required
                  className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-red-600 sm:text-sm sm:leading-6"
                />
              </div>
            </div>

            <div>
              <div className="flex items-center justify-between">
                <label htmlFor="password" className="block text-sm font-medium leading-6 text-gray-300">
                  Senha
                </label>
                <div className="text-sm">
                  <a href="#" className="font-semibold text-red-600 hover:text-red-500">
                    Esqueci minha senha?
                  </a>
                </div>
              </div>
              <div className="mt-2">
                <input
                  value={values.password}
                  onChange={handleChange}
                  id="password"
                  name="password"
                  type="password"
                  autoComplete="current-password"
                  required
                  className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-red-600 sm:text-sm sm:leading-6"
                />
              </div>
            </div>

            <div>
              <button
                type="submit"
                className="flex w-full justify-center rounded-md bg-red-600 px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-red-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"
              >
                Entrar
              </button>
            </div>
          </form>

          <p className="mt-10 text-center text-sm text-gray-500">
            Ainda não possui uma conta?{' '}
            <Link to="/signup" className="font-semibold leading-6 text-red-600 hover:text-red-500">Crie uma conta</Link>
          </p>
        </div>
      </div>
      </div>
    </div>
  )
}


export default Signin