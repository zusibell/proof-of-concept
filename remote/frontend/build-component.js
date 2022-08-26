const fs = require('fs-extra');
const concat = require('concat');
    
build = async () =>{
    const files = [
        './dist/frontend/runtime.js',
        './dist/frontend/polyfills.js',
        './dist/frontend/main.js'
    ];
        
    await fs.ensureDir('widget');
    await concat(files, 'widget/overview-widget.js');
}
build();