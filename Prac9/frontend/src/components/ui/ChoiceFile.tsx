import {FC, useState} from "react";
import axios from "axios";
import {BACKEND_URL} from "../../http";

interface Props {
    updateFiles?: () => any;
    id?: string
}

export const ChoiceFile: FC<Props> = ({updateFiles, id}) => {
    const [isLoading, setIsLoading] = useState<boolean>(false)
    const [files, setFiles] = useState<FileList | null>(null)

    const loadFiles = () => {
        if (files === null) {
            return
        }
        setIsLoading(true);
        const requestPromises = Array.from(files).map(file => {
            const data = new FormData();
            data.append("file", file);
            return axios.request({
                method: id ? "put" : "post",
                maxBodyLength: Infinity,
                url: `${BACKEND_URL}/api/files` + (id ? `/${id}` : ""),
                headers: {
                    'Content-Type': 'multipart/form-data',
                    'Accept': "*/*"
                },
                data: data
            });
        });

        Promise.all(requestPromises)
            .then(() => {
                updateFiles?.();
            })
            .catch(error => {
                console.error('Ошибка при загрузке файлов', error);
            })
            .finally(() => setIsLoading(false));
    }


    return (
        <div className="border border-2 rounded rounded-2">
            <div className="m-4 mb-0">
                <input
                    onChange={event => setFiles(event.target.files)}
                    className="form-control" type="file"/>
            </div>
            <div className="m-4 btn btn-primary" onClick={loadFiles}>
                {isLoading ? <div className={"spinner-border spinner-border-sm"}/> : "upload file"}
            </div>
        </div>
    );
};