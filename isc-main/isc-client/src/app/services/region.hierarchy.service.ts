import { RestService } from '@common/services/rest.service';
import { CompositeNode } from 'app/domain/composite';

class RegionHierarchyService extends RestService {
    getComposite(regionId: string): Promise<CompositeNode> {
        return new Promise((resolve, reject) => {
            this.client
                .get<CompositeNode, CompositeNode>(`/rest/composite/${regionId}`, {
                    headers: {
                        ...this.defaultHeaders(),
                    },
                })
                .then(rez => {
                    resolve(rez);
                })
                .catch(rez => reject(rez));
        });
    }
}
const regionHierarchyService = new RegionHierarchyService();
export default regionHierarchyService;
