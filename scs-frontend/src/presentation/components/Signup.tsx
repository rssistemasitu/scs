import React, { useState } from 'react'
import { useNavigate } from 'react-router-dom';
import { SignupUserType, signupUser } from '../api/Api';

const Signup = () => {
  const [values, setValues] = useState<SignupUserType>(
    {
      first_name: '',
      last_name: '',
      email: '',
      password: '',
      cpf: '',
      date_birth: '',
      card_name: '',
      card_number: '',
      expiration_date: '',
      cvv: 0,
    }
  );
  const [errors, setErrors] = useState<Object>({})

  const navigate = useNavigate();

  const limparFormulario = () => {
    setValues({
      first_name: '',
      last_name: '',
      email: '',
      password: '',
      cpf: '',
      date_birth: '',
      card_name: '',
      card_number: '',
      expiration_date: '',
      cvv: 0,
    });
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setValues((prevState: any) => ({
      ...prevState,
      [name]: value,
      
    }));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    // Lógica para enviar o formulário

    if (!values) {
      setErrors("Preencha todos os campos");
      console.log(errors);
      return;
    }

    await signupUser(values).then((res) => {
      console.log(res)
      navigate("/signin")
    }).catch((e) => {
      setErrors(e)
    })
  };



  return (
    <div className="w-full min-h-screen bg-black">
      <div className="h-full">
        <div className="flex min-h-full flex-1 flex-col justify-center px-6 py-12 lg:px-8">
          <div className="mt-4 sm:mx-auto sm:w-full sm:max-w-sm">
            <h2 className="text-center text-2xl font-bold leading-9 tracking-tight text-white">
              Criar conta
            </h2>
            <form action="#" method="POST" onSubmit={handleSubmit}>
              <div className="space-y-12">
                <div className="border-b border-gray-900/10 pb-12">
                  <div className="mt-5 grid grid-cols-1 gap-x-6 gap-y-8 sm:grid-cols-6">
                    <div className="sm:col-span-3">
                      <label htmlFor="first_name" className="block text-sm font-medium leading-6 text-gray-300">
                        Nome
                      </label>
                      <div className="mt-2">
                        <input
                          value={values.first_name}
                          onChange={handleChange}
                          type="text"
                          name="first_name"
                          id="first_name"
                          autoComplete="off"
                          className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-red-600 sm:text-sm sm:leading-6"
                        />
                      </div>
                    </div>
                    <div className="sm:col-span-3">
                      <label htmlFor="last_name" className="block text-sm font-medium leading-6 text-gray-300">
                        Sobrenome
                      </label>
                      <div className="mt-2">
                        <input
                          value={values.last_name}
                          onChange={handleChange}
                          type="text"
                          name="last_name"
                          id="last_name"
                          autoComplete="off"
                          className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-red-600 sm:text-sm sm:leading-6"
                        />
                      </div>
                    </div>
                  </div>
                  <div className="mt-5 grid grid-cols-1 gap-x-6 gap-y-8 sm:grid-cols-6">
                    <div className="sm:col-span-6">
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
                          autoComplete="off"
                          className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-red-600 sm:text-sm sm:leading-6"
                        />
                      </div>
                    </div>
                  </div>
                  <div className="mt-5 grid grid-cols-1 gap-x-6 gap-y-8 sm:grid-cols-6">
                    <div className="col-span-3">
                      <label htmlFor="password" className="block text-sm font-medium leading-6 text-gray-300">
                        Senha
                      </label>
                      <div className="mt-2">
                        <input
                          value={values.password}
                          onChange={handleChange}
                          type="password"
                          name="password"
                          id="password"
                          autoComplete="off"
                          className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-red-600 sm:text-sm sm:leading-6"
                        />
                      </div>
                    </div>
                    <div className="col-span-3">
                      <label htmlFor="cpf" className="block text-sm font-medium leading-6 text-gray-300">
                        CPF
                      </label>
                      <div className="mt-2">
                        <input
                          value={values.cpf}
                          onChange={handleChange}
                          type="text"
                          name="cpf"
                          id="cpf"
                          autoComplete="off"
                          className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-red-600 sm:text-sm sm:leading-6"
                        />
                      </div>
                    </div>
                  </div>
                  <div className="mt-5 grid grid-cols-1 gap-x-6 gap-y-8 sm:grid-cols-6">
                    <div className="sm:col-span-6">
                      <label htmlFor="date_birth" className="block text-sm font-medium leading-6 text-gray-300">
                        Data de Nascimento
                      </label>
                      <div className="mt-2">
                        <input
                          value={values.date_birth}
                          onChange={handleChange}
                          type="date"
                          name="date_birth"
                          id="date_birth"
                          autoComplete="off"
                          className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-red-600 sm:text-sm sm:leading-6"
                        />
                      </div>
                    </div>
                  </div>

                  <div className="mt-5 grid grid-cols-1 gap-x-6 gap-y-8 sm:grid-cols-6">
                  <div className="col-span-full">
                      <label htmlFor="card_name" className="block text-sm font-medium leading-6 text-gray-300">
                        Nome no Cartão
                      </label>
                      <div className="mt-2">
                        <input
                          value={values.card_name}
                          onChange={handleChange}
                          type="text"
                          name="card_name"
                          id="card_name"
                          autoComplete="off"
                          className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-red-600 sm:text-sm sm:leading-6"
                        />
                      </div>
                    </div>
                    <div className="col-span-full">
                      <label htmlFor="card_number" className="block text-sm font-medium leading-6 text-gray-300">
                        Número do Cartão
                      </label>
                      <div className="mt-2">
                        <input
                          value={values.card_number}
                          onChange={handleChange}
                          type="number"
                          name="card_number"
                          id="card_number"
                          autoComplete="off"
                          className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-red-600 sm:text-sm sm:leading-6"
                        />
                      </div>
                    </div>
                  </div>
                  <div className="mt-5 grid grid-cols-1 gap-x-6 gap-y-8 sm:grid-cols-6">
                    <div className="col-span-3">
                      <label htmlFor="expiration_date" className="block text-sm font-medium leading-6 text-gray-300">
                        Data de Expiração
                      </label>
                      <div className="mt-2">
                        <input
                          value={values.expiration_date}
                          onChange={handleChange}
                          type="text"
                          name="expiration_date"
                          id="expiration_date"
                          autoComplete="off"
                          className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-red-600 sm:text-sm sm:leading-6"
                        />
                      </div>
                    </div>
                    <div className="col-span-3">
                      <label htmlFor="cvv" className="block text-sm font-medium leading-6 text-gray-300">
                        CVV
                      </label>
                      <div className="mt-2">
                        <input
                          value={values.cvv}
                          onChange={handleChange}
                          type="text"
                          name="cvv"
                          id="cvv"
                          autoComplete="off"
                          className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-red-600 sm:text-sm sm:leading-6"
                        />
                      </div>
                    </div>
                  </div>

                </div>
              </div>

              <div className="mt-1 flex items-center justify-center gap-x-6">
                <button type="button" className="rounded-md bg-yellow-600 px-3 py-2 text-sm font-semibold text-white shadow-sm hover:bg-yellow-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-yellow-600">
                  Cancelar
                </button>
                <button
                  type="submit"
                  className="rounded-md bg-green-600 px-3 py-2 text-sm font-semibold text-white shadow-sm hover:bg-green-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-green-600"
                >
                  Salvar
                </button>
              </div>
            </form>
          </div>
        </div>
      </div >
    </div >
  )
}

export default Signup