import {FC} from "react";

interface Props {
    row: Object
}

export const RowView: FC<Props> = ({row}) => {
    return (
        <tr>
            {Object.values(row).map((value) =>
                <td>{value}</td>
            )}
        </tr>
    );
};