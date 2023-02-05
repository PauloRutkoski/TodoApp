import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { ElevatedButton } from "../../styles/style";

export default function LoadingButton({children, loading, ...rest}) {
    return (
        <ElevatedButton {...rest} disabled={loading === 'true'} >
            {loading ? <FontAwesomeIcon icon="spinner" className="fa-spin" /> : children}
        </ElevatedButton>
    );
}