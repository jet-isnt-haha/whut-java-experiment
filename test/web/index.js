var translateL='-50%';
var translateT='-50%';
//拖动事件
function onDrag(e)
    {
        const dragWindow=document.querySelector('.drag-window');
        const container=document.querySelector('.container');
        const windowing=document.querySelector('.windowing');
        const maximize =document.querySelector('.maximize');
        let oddStyle=window.getComputedStyle(container);
        let oddStyleDrag=window.getComputedStyle(dragWindow);
        const containerRect=container.getBoundingClientRect();
        let left=parseFloat(oddStyle.left);
        let top=parseFloat(oddStyle.top);
        if(window.getComputedStyle(maximize).visibility==='hidden')//最大化时拖动窗口情况
            {
            const mouseOffsetX = e.clientX - left;//计算鼠标相对于容器水平向左方向的偏移量
            const mouseOffsetY = e.clientY - top;//计算鼠标相对于容器垂直向上方向的偏移量
            container.style.width='600px';
            container.style.height='600px';
            //将窗口的移动到其大小的左上角再稍微调整
            translateL=`${mouseOffsetX-300}px`;
            var translateT=`${mouseOffsetY-parseFloat(oddStyleDrag.height)/2}px`;
            container.style.transform=`translate(${translateL},${translateT})`;
            //窗口化隐藏并且禁用其放大事件
            windowing.style.visibility='hidden';
            windowing.style.pointerEvent='none';
            //将最大化图标按钮显示并启用其窗口化事件
            maximize.style.visibility='visible';
            maximize.style.pointerEvent='visible';}
        container.style.left=`${left+e.movementX}px`;
        container.style.top=`${top+e.movementY}px`;
    }
const dragElement=document.querySelector('.drag-window');
dragElement.addEventListener('mousedown',(e)=>{
    if(e.target.classList.contains('close')||e.target.classList.contains('minimize')||
        e.target.classList.contains('maximize')||e.target.classList.contains('windowing'))//触碰最大化、最小化、窗口化、关闭无法拖动
    {
    return;
    }
    document.addEventListener('mousemove',onDrag)
    document.addEventListener('mouseup',()=>
    {
        document.removeEventListener('mousemove',onDrag)
    })
})





//点击按钮关闭事件
function close(e)
    {
         // 确保点击的是 close 按钮
         if (e.target.classList.contains('close')) {
            // 选择 iframe 元素
            const iframe = document.querySelector('iframe');
            // 如果 iframe 存在，移除它
            if (iframe) {
                document.querySelector('.container').remove();
            }
        }
    }
//点击按钮最小化事件
function minimize(e)
{
    //与close类似
    if(e.target.classList.contains('minimize')){
        //
        const iframe =document.querySelector('iframe');
        if(iframe)
        {
            const container =document.querySelector('.container');
            const minIcon=document.querySelector('.minimize-icon');//
            const containerRect=container.getBoundingClientRect();
            const iconRect=minIcon.getBoundingClientRect();//获得最小化图标的位置
            const stylesheets =document.styleSheets;
            let translateX,translateY;
            //最小化窗口动画判断******
            if((iconRect.left>=containerRect.left)&&(containerRect.left+containerRect.width>=iconRect.left+iconRect.width))
            {
                translateX ='-50%';translateY=iconRect.bottom+containerRect.width / 2-containerRect.top;
                transformOrigin='bottom';
            }
            else if(containerRect.left+containerRect.width<iconRect.left+iconRect.width){
                translateX =`${iconRect.right -containerRect.right}px`;translateY=containerRect.bottom+containerRect.width / 2-iconRect.top;
                transformOrigin='right bottom';
                minIcon.style.transformOrigin='right top';
            }
            else {translateX =`${iconRect.left - containerRect.right}px`;translateY=containerRect.bottom+containerRect.width / 2-iconRect.top;
            minIcon.style.transformOrigin='left top';
                transformOrigin='left bottom';}
                for(let i=0;i<stylesheets.length;i++)
                {
                    const stylesheet=stylesheets[i];
                    //检查是否是外部引入的css文件
                    if(stylesheet.href&&stylesheet.href.includes('style.css')){
                        stylesheet.insertRule(`
                            @keyframes
                            minimize-ani{
                            from{transform:translate(${translateL},${translateT}) scale(1);
                            opacity:1;
                            }
                            to{transform:translate(${translateX},${translateY}px) scale(0.1);
                            opacity:0;
                            }
                            }
                        `,stylesheet.cssRules.length)
                    } 
                    break;
                }
                //监测是否触发minIcon的动画的函数(*使用了递归使得其越来越向满足的条件靠近)
                function checkCollision() {
                    const containerRect = container.getBoundingClientRect();
                    const iconRect = minIcon.getBoundingClientRect();
                
                    // 检查 container 的底部是否触碰到 minIcon 的顶部
                    if (containerRect.bottom >= iconRect.top) {
                        // 执行动画逻辑
                        minIcon.style.visibility = 'visible';
                        minIcon.style.transition = 'transform 0.1s ease';
                        minIcon.style.transform = 'scale(0.5)';
                        setTimeout(() => {
                            minIcon.style.transform = 'scale(1)';
                        }, 250);
                        // 停止进一步的检查
                        return;
                    }
                    // 如果未发生碰撞，则继续下一帧requestAnimationFrame
                    requestAnimationFrame(checkCollision);
                }
                // 开始动画和碰撞检测
                container.style.animation ="minimize-ani 0.5s forwards";
                requestAnimationFrame(checkCollision);
                // container.style.transition = 'transform 2s ease';
                // container.style.transform = 'translate(-50%, 50px)';用js的动画
                setTimeout(()=>{
                document.querySelector('.container').style.visibility='hidden';
                },100);
        }
        
    }
}
//点击按钮最大化事件
function maximize(e)
{
    //判断是否选择到最大化按钮
    if(e.target.classList.contains('maximize')){
        const container =document.querySelector('.container');
        const iframe =document.querySelector('iframe');
        const containerRect =container.getBoundingClientRect();//获取当前元素信息
        const maximize =document.querySelector('.maximize');
        const windowing=document.querySelector('.windowing');
        if(iframe)
        {
        translateL='-50%';translateT='-50%';
        container.style.transform='translate(-50%,-50%)';//改变L与T的值且重新移动container的位置确保其在中心展开以防移动位置的窗口化使其全屏不完整
        //使其填充整个页面且与任务栏恰好触碰
        container.style.top='46.5%';
        container.style.left='50%'; 
        container.style.width='100vw';
        container.style.height='93vh';
        //最大化图标隐藏并且禁用其放大事件
        maximize.style.visibility='hidden';
        maximize.style.pointerEvent='none';
        //将窗口化按钮显示并启用其窗口化事件
        windowing.style.visibility='visible';
        windowing.style.pointerEvent='visible';
    }
    }
}
//点击按钮窗口化事件
function windowing(e)
{
    if(e.target.classList.contains('windowing'))
    {
            const container =document.querySelector('.container');
            const iframe =document.querySelector('iframe');
            const containerRect =container.getBoundingClientRect();//获取当前元素信息
            const maximize =document.querySelector('.maximize');
            const windowing=document.querySelector('.windowing');
            if(iframe)
            {
            //使其返回最开始的中心位置且长宽还原
            container.style.top='50%';
            container.style.left='50%'; 
            container.style.width='600px';
            container.style.height='600px';
            //窗口化隐藏并且禁用其放大事件
            windowing.style.visibility='hidden';
            windowing.style.pointerEvent='none';
            //将最大化图标按钮显示并启用其窗口化事件
            maximize.style.visibility='visible';
            maximize.style.pointerEvent='visible';
        }
    }
}



const dragBarClickElement =document.querySelector('.drag-window');
dragBarClickElement.addEventListener('click',close);
dragBarClickElement.addEventListener('click',minimize);
dragBarClickElement.addEventListener('click',maximize);
dragBarClickElement.addEventListener('click',windowing);

// function display(e)
// {
//     // 检查点击的是“最小化图标”
//     if(e.target.classList.contains('minimize-icon')) 
//     {
//         const container = document.querySelector('.container');

//         // 确保窗口可见，并恢复其样式
//         container.style.visibility = 'visible';  // 显示窗口
//         container.style.opacity = '1';  // 使窗口不透明
//         container.style.transform = 'translate(-50%, -50%)';  // 恢复居中位置
//         container.style.width = '600px';  // 恢复窗口宽度
//         container.style.height = '600px';  // 恢复窗口高度
//         container.style.top = '50%';  // 恢复居中顶部位置
//         container.style.left = '50%';  // 恢复居中左侧位置
//         container.style.display = 'block';  // 确保display属性为block
//         container.style.scale = '1';  // 确保缩放比例为1

//         console.log('窗口已显示');
//     }
// }
function display(e) {
    if (e.target.classList.contains('minimize-icon')) {
        const container = document.querySelector('.container');
        container.style.removeProperty('animation');
        container.style.visibility = 'visible'; // 确保窗口可见
        container.style.transform = 'translate(-50%, -50%) scale(1)'; // 重置位置和大小
        container.style.zIndex = '1000'; // 提升层级，防止被其他元素覆盖
        container.style.opacity = '1'; // 确保不透明
        console.log('显示窗口');
    }
}

const iconClickElement=document.querySelector('.icon');
iconClickElement.addEventListener('click',display);