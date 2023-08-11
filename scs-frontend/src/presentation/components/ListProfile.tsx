import { useEffect, useState } from 'react'
import { PaperClipIcon } from '@heroicons/react/20/solid'
import { UserFull, getUserFull } from '../api/Api';
import UseAuth from '../hooks/UseAuth';
import Dropdown from './Dropdown';

export const ListProfile = () => {
  const { user, isAuthenticated } = UseAuth();
  const [userFull, setUserFull] = useState<UserFull | null>(null);
  const [errors, setErrors] = useState();

  useEffect(() => {
    getUserFull(user.user.token)
      .then((res) => {
        setUserFull(res)
      }).catch((e) => {
        setErrors(e)
      });

  }, []);


  return (
    <div className='pt-28'>
      <div className="px-3 rounded-lg ">
        <div className="border border-gray-100 px-3 pt-8 mb-6 rounded-lg ">
          <div className="mx-5 sm:px-0">
            <h1 className="text-base font-semibold leading-7 text-gray-300">Perfil</h1>
          </div>
          <div className='mx-5'>
            <dl className="divide-y divide-gray-100">
              <div className="py-6 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-0">
                <dt className="text-sm font-medium leading-6 text-gray-500">Nome Completo</dt>
                <dd className="mt-1 text-sm leading-6 text-gray-100 sm:col-span-2 sm:mt-0">{userFull?.firstName} {userFull?.lastName}</dd>
              </div>
              <div className="py-6 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-0">
                <dt className="text-sm font-medium leading-6 text-gray-500">E-mail</dt>
                <dd className="mt-1 text-sm leading-6 text-gray-100 sm:col-span-2 sm:mt-0">{userFull?.email}</dd>
              </div>
              <div className="py-6 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-0">
                <dt className="text-sm font-medium leading-6 text-gray-500">CPF</dt>
                <dd className="mt-1 text-sm leading-6 text-gray-100 sm:col-span-2 sm:mt-0">{userFull?.cpf}</dd>
              </div>

              <div className="py-6 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-0">
                <dt className="text-sm font-medium leading-6 text-gray-500">Data de Nascimento</dt>
                <dd className="mt-1 text-sm leading-6 text-gray-100 sm:col-span-2 sm:mt-0">{userFull?.dateBirth}</dd>
              </div>
              <div className="py-6 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-0">
                <dt className="text-sm font-medium leading-6 text-gray-500">Nome Cartão</dt>
                <dd className="mt-1 text-sm leading-6 text-gray-100 sm:col-span-2 sm:mt-0">{userFull?.cardName}</dd>
              </div>
              <div className="py-6 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-0">
                <dt className="text-sm font-medium leading-6 text-gray-500">Número do Cartão</dt>
                <dd className="mt-1 text-sm leading-6 text-gray-100 sm:col-span-2 sm:mt-0">{userFull?.cardNumber}</dd>
              </div>
              <div className="py-6 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-0">
                <dt className="text-sm font-medium leading-6 text-gray-500">Data de Expiração</dt>
                <dd className="mt-1 text-sm leading-6 text-gray-100 sm:col-span-2 sm:mt-0">{userFull?.expirationDate}</dd>
              </div>
              <div className="py-6 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-0">
                <dt className="text-sm font-medium leading-6 text-gray-500">CVV</dt>
                <dd className="mt-1 text-sm leading-6 text-gray-100 sm:col-span-2 sm:mt-0">{userFull?.cvv}</dd>
              </div>
              <div className="py-6 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-0">
                <dt className="text-sm font-medium leading-6 text-gray-500">Número do Cartão</dt>
                <dd className="mt-1 text-sm leading-6 text-gray-100 sm:col-span-2 sm:mt-0">{userFull?.cardNumber}</dd>
              </div>
              <div className="py-6 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-0">
                <dt className="text-sm font-medium leading-6 text-gray-500">Status</dt>
                <dd className="mt-1 text-sm leading-6 text-gray-100 sm:col-span-2 sm:mt-0">{userFull?.status ? "Ativo" : "Desativado"}</dd>
              </div>
              <div className="py-6 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-0">
                <dt className="text-sm font-medium leading-6 text-gray-500">É proprietário?</dt>
                <dd className="mt-1 text-sm leading-6 text-gray-100 sm:col-span-2 sm:mt-0">{userFull?.cardNumber}</dd>
              </div>
            </dl>
          </div>
        </div>
      </div>
      
      <div>
        <h1 className='mx-11 text-base font-semibold leading-7 text-gray-300'>Dependentes: {userFull?.userDependents.length}
        </h1>
        </div>
      <div>
        {userFull?.userDependents && userFull?.userDependents.map((u, i) => (
          <div key={i} className="px-3 rounded-lg ">
            <div className="border border-gray-100 px-3  mb-6 rounded-lg ">
              <div className='mx-5'>
                <dl className="divide-y divide-gray-100">
                  <div className="py-6 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-0">
                    <dt className="text-sm font-medium leading-6 text-gray-500">Nome Completo</dt>
                    <dd className="mt-1 text-sm leading-6 text-gray-100 sm:col-span-2 sm:mt-0">{u.firstName} {u.lastName}</dd>
                  </div>
                  <div className="py-6 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-0">
                    <dt className="text-sm font-medium leading-6 text-gray-500">E-mail</dt>
                    <dd className="mt-1 text-sm leading-6 text-gray-100 sm:col-span-2 sm:mt-0">{u.email}</dd>
                  </div>
                  <div className="py-6 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-0">
                    <dt className="text-sm font-medium leading-6 text-gray-500">CPF</dt>
                    <dd className="mt-1 text-sm leading-6 text-gray-100 sm:col-span-2 sm:mt-0">{u.cpf}</dd>
                  </div>
                  <div className="py-6 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-0">
                    <dt className="text-sm font-medium leading-6 text-gray-500">Data de Nascimento</dt>
                    <dd className="mt-1 text-sm leading-6 text-gray-100 sm:col-span-2 sm:mt-0">{u.dateBirth}</dd>
                  </div>
                  <div className="py-6 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-0">
                <dt className="text-sm font-medium leading-6 text-gray-500">Status</dt>
                <dd className="mt-1 text-sm leading-6 text-gray-100 sm:col-span-2 sm:mt-0">{u?.status ? "Ativo" : "Desativado"}</dd>
              </div>
                  

                </dl>
              </div>
            </div>
          </div>
        ))}

      </div>
    </div>
  )
}


