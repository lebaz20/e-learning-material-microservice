export class Product {
    constructor(
        public id?: number,
        public productCode?: string,
        public productTitle?: string,
        public imageUrl?: string,
        public quantity?: number,
        public price?: number,
    ) {}
}