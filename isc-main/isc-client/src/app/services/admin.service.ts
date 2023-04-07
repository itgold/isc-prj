import { RestService } from '@common/services/rest.service';

export interface SyncResponse {
    status: string;
}

class AdminService extends RestService {
    syncDoors(): Promise<SyncResponse> {
        return new Promise((resolve, reject) => {
            this.client
                .post('/rest/admin/integration/sync/doors', {
                    headers: {
                        ...this.defaultHeaders(),
                    },
                })
                .then(rez => resolve(rez.data as SyncResponse))
                .catch(rez => reject(rez));
        });
    }

    syncCameras(): Promise<SyncResponse> {
        return new Promise((resolve, reject) => {
            this.client
                .post('/rest/admin/integration/sync/cameras', {
                    headers: {
                        ...this.defaultHeaders(),
                    },
                })
                .then(rez => resolve(rez.data as SyncResponse))
                .catch(rez => reject(rez));
        });
    }

    syncUsers(): Promise<SyncResponse> {
        return new Promise((resolve, reject) => {
            this.client
                .post('/rest/admin/integration/sync/users', {
                    headers: {
                        ...this.defaultHeaders(),
                    },
                })
                .then(rez => resolve(rez.data as SyncResponse))
                .catch(rez => reject(rez));
        });
    }

    syncRadios(): Promise<SyncResponse> {
        return new Promise((resolve, reject) => {
            this.client
                .post('/rest/admin/integration/sync/radios', {
                    headers: {
                        ...this.defaultHeaders(),
                    },
                })
                .then(rez => resolve(rez.data as SyncResponse))
                .catch(rez => reject(rez));
        });
    }

    syncAll(): Promise<SyncResponse> {
        return new Promise((resolve, reject) => {
            this.client
                .post('/rest/admin/integration/sync/all', {
                    headers: {
                        ...this.defaultHeaders(),
                    },
                })
                .then(rez => resolve(rez.data as SyncResponse))
                .catch(rez => reject(rez));
        });
    }

    rebuildCompositeTree(): Promise<SyncResponse> {
        return new Promise((resolve, reject) => {
            this.client
                .post('/rest/admin/compositeTree/rebuild', {
                    headers: {
                        ...this.defaultHeaders(),
                    },
                })
                .then(rez => resolve(rez.data as SyncResponse))
                .catch(rez => reject(rez));
        });
    }

    updateDoorRegions(): Promise<SyncResponse> {
        return new Promise((resolve, reject) => {
            this.client
                .post('/rest/admin/assign-doors', {
                    headers: {
                        ...this.defaultHeaders(),
                    },
                })
                .then(rez => resolve(rez.data as SyncResponse))
                .catch(rez => reject(rez));
        });
    }

    updateSpeakersRegions(): Promise<SyncResponse> {
        return new Promise((resolve, reject) => {
            this.client
                .post('/rest/admin/assign-speakers', {
                    headers: {
                        ...this.defaultHeaders(),
                    },
                })
                .then(rez => resolve(rez.data as SyncResponse))
                .catch(rez => reject(rez));
        });
    }

    updateCamerasRegions(): Promise<SyncResponse> {
        return new Promise((resolve, reject) => {
            this.client
                .post('/rest/admin/assign-cameras', {
                    headers: {
                        ...this.defaultHeaders(),
                    },
                })
                .then(rez => resolve(rez.data as SyncResponse))
                .catch(rez => reject(rez));
        });
    }
}

const adminService = new AdminService();
export default adminService;
