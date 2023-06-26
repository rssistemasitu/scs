import Filme from '../../assets/filme.jpg'
import { PlayCircleIcon, PlusCircleIcon } from '@heroicons/react/24/solid'

export const ShowCase = () => {
  return (
    <div className='w-full h-[500px]'>
        {/* Background Image */}
        <img src={Filme} alt="" className='z-0 absolute left-0 w-screen h-auto'/>

        <div className="flex p-4 max-w-7xl items-center h-full">
        <div className="flex flex-col z-10">
            <h2 className="text-4xl font-semibold text-gray-200">Velozes e Furiosos 10</h2>
            <p className="text-md text-gray-300 max-w-xl my-2">
            É simplesmente uma simulação de texto da indústria tipográfica e de impressos, e vem sendo utilizado desde o século XVI, quando um impressor desconhecido pegou uma bandeja de tipos e os embaralhou para fazer um livro de modelos de tipos. Lorem Ipsum sobreviveu não só a cinco séculos, como também ao salto para a editoração eletrônica, permanecendo essencialmente inalterado.
            </p>

            <div className="flex m-4 gap-5 text-gray-400">
                <span className="text-sm">2023</span>|
                <span className="text-sm">23 Episódios</span>|
                <span className="text-sm">16+</span>
            </div>

            <div className="flex gap-5 m-4 flex-wrap">
                <button className='p-2 flex hover:bg-red-500 rounded-full gap-2 bg-red-600 text-white text-sm font-semibold'>
                    <PlayCircleIcon className='w-5'/>Assistir 
                </button>
                <button className='p-2 flex hover:bg-gray-800 rounded-full gap-2 bg-gray-900 text-white text-sm font-semibold'>
                    <PlusCircleIcon className='w-5'/>Adicionar na Lista
                </button>
            </div>
        </div>
        </div>
    </div>
  )
}
