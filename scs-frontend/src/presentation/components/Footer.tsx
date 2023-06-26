import logo from '../../assets/logo.svg'

const Footer = () => {
  return (
    <div className='w-screen p4 md:px-6 border-t-2 mt-auto py-8 border-gray-800'>
        <div className="flex flex-wrap justify-between">
            <div className="flex items-center">
                <img src={logo} alt="" className='w-20'/>
                <span className="text-gray-400 mx-5 text-sm">&copy; 2023. Todos os direitos reservados</span>
            </div>
            <span className="text-gray-400 mx-6 font-semibold">SGS</span>
        </div>
    </div>
  )
}

export default Footer