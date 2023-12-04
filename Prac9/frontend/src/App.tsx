import {TableView} from "./components/ui/Table/TableView.tsx";
import {ReactNode, useEffect, useState} from "react";
import axios from "axios";
import {ChoiceFile} from "./components/ui/ChoiceFile.tsx";
import {ModalWindow} from "./components/ui/ModalWindow.tsx";
import {BACKEND_URL} from "./http";

export interface FileResponse {
    Id: string,
    Name: string
}

function App() {

    const [files, setFiles] = useState<FileResponse[] | null>(null)
    const getAllFiles = () => {
        axios.get(`${BACKEND_URL}/api/files`)
            .then(response => {
                setFiles(response.data);
                setTimeout(getAllFiles, 1000);
            })
            .catch(() => {
                setTimeout(getAllFiles, 1000);
            });
    }

    useEffect(() => {
        getAllFiles()
    }, []);


    const deleteFile = (id: string) => {
        axios.delete(`${BACKEND_URL}/api/files/${id}`).then(() => getAllFiles());
    }

    const [active, setActive] = useState<boolean>(false)
    const [content, setContent] = useState<ReactNode>(<></>)

    return (
        <>
            <div className="m-4">
                <ChoiceFile updateFiles={() => getAllFiles()}/>
            </div>

            <div className="border-top m-4 border-2"/>

            {files !== null && <div className="m-4 pt-1 border border-2 rounded rounded-2">
                <TableView
                    head={[...Object.keys(files[0]), "delete", "info", "update", "download"]}
                    rows={files.map(item => {
                        return {
                            ...item,
                            delete: <>
                                <div className="btn btn-danger" onClick={() => deleteFile(item.Id)}>delete</div>
                            </>,
                            info: <>
                                <div className="btn btn-secondary" onClick={() => {
                                    axios.get(`${BACKEND_URL}/api/files/info/${item.Id}`)
                                        .then(response => {
                                            console.log(response.data)
                                            setContent(
                                                <div className="mt-2 mb-2">
                                                    <TableView
                                                        head={Object.keys(response.data)}
                                                        rows={[
                                                            JSON.parse(
                                                                JSON.stringify(response.data),
                                                                (_key, value) => {
                                                                    if (value === null) {
                                                                        return "отсутствует";
                                                                    }
                                                                    return value;
                                                                }
                                                            )
                                                        ]}
                                                    />
                                                </div>
                                            );
                                            setActive(true);

                                        })
                                }}>info
                                </div>
                            </>,
                            update: <>
                                <div className="btn btn-primary" onClick={() => {
                                    setContent(
                                        <ChoiceFile
                                            updateFiles={() => getAllFiles()}
                                            id={item.Id}
                                        />
                                    );
                                    setActive(true);
                                }}>update
                                </div>
                            </>,
                            download: <>
                                <a className="btn btn-success"
                                   href={`${BACKEND_URL}/api/files/${item.Id}`}>download</a>
                            </>,
                        }
                    })}
                />
            </div>}

            <ModalWindow active={active} setActive={setActive}>
                <div>
                    {content}
                </div>
            </ModalWindow>
        </>
    )
}

export default App
