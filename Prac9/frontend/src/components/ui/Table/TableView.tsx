import {FC} from "react";
import {RowView} from "./RowView.tsx";

interface TableProps {
    head: string[]
    rows: Object[]
}

export const TableView: FC<TableProps> = ({head, rows}) => {
    return (
        <div className="table-responsive">
            <table className="table table-striped">
                <thead>
                <tr>
                    {head.map((item) => <th scope="col">{item}</th>)}
                </tr>
                </thead>
                <tbody>
                {rows.map(row =>
                    <RowView
                        row={row}
                    />
                )}
                </tbody>
            </table>
        </div>
    );
};