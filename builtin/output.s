	.section	.data
global0:
	.zero	4
global1:
	.zero	4
	.section	.rodata
	.section	text
	.globl	main
main:
.main:
	addi	sp,sp,-720
	sw	ra,716(sp)
.init_global:
	li	t3,105
	sw	t3,676(sp)
	lw	t3,676(sp)
	sw	t3,global0,t5
	sw	t5,672(sp)
	li	t3,100005
	sw	t3,668(sp)
	lw	t3,668(sp)
	sw	t3,global1,t5
	sw	t5,664(sp)
	j	.main.entry
.main.entry:
	li	t3,712
	sw	t3,660(sp)
	lw	t4,660(sp)
	add	t5,sp,t4
	sw	t5,656(sp)
	li	t3,0
	sw	t3,652(sp)
	lw	t4,656(sp)
	lw	t3,652(sp)
	sw	t3,0(t4)
	li	t3,708
	sw	t3,648(sp)
	lw	t4,648(sp)
	add	t5,sp,t4
	sw	t5,644(sp)
	li	t3,704
	sw	t3,640(sp)
	lw	t4,640(sp)
	add	t5,sp,t4
	sw	t5,636(sp)
	li	t3,700
	sw	t3,632(sp)
	lw	t4,632(sp)
	add	t5,sp,t4
	sw	t5,628(sp)
	li	t3,696
	sw	t3,624(sp)
	lw	t4,624(sp)
	add	t5,sp,t4
	sw	t5,620(sp)
	li	t3,692
	sw	t3,616(sp)
	lw	t4,616(sp)
	add	t5,sp,t4
	sw	t5,612(sp)
	li	t3,688
	sw	t3,608(sp)
	lw	t4,608(sp)
	add	t5,sp,t4
	sw	t5,604(sp)
	lw	t3,global0
	sw	t3,600(sp)
	lw	t3,600(sp)
	mv	a0,t3
	call	__mallocArray__
	mv	t5,a0
	sw	t5,596(sp)
	lw	t4,604(sp)
	lw	t3,596(sp)
	sw	t3,0(t4)
	li	t3,684
	sw	t3,592(sp)
	lw	t4,592(sp)
	add	t5,sp,t4
	sw	t5,588(sp)
	lw	t3,global0
	sw	t3,584(sp)
	lw	t3,584(sp)
	mv	a0,t3
	call	__mallocArray__
	mv	t5,a0
	sw	t5,580(sp)
	lw	t4,588(sp)
	lw	t3,580(sp)
	sw	t3,0(t4)
	li	t3,680
	sw	t3,576(sp)
	lw	t4,576(sp)
	add	t5,sp,t4
	sw	t5,572(sp)
	lw	t3,global1
	sw	t3,568(sp)
	lw	t3,568(sp)
	mv	a0,t3
	call	__mallocArray__
	mv	t5,a0
	sw	t5,564(sp)
	lw	t4,572(sp)
	lw	t3,564(sp)
	sw	t3,0(t4)
	call	__getInt__
	mv	t5,a0
	sw	t5,560(sp)
	lw	t4,628(sp)
	lw	t3,560(sp)
	sw	t3,0(t4)
	call	__getInt__
	mv	t5,a0
	sw	t5,556(sp)
	lw	t4,620(sp)
	lw	t3,556(sp)
	sw	t3,0(t4)
	li	t3,0
	sw	t3,552(sp)
	lw	t4,612(sp)
	lw	t3,552(sp)
	sw	t3,0(t4)
	j	.for.0.condition
.for.0.condition:
	lw	t4,612(sp)
	lw	t5,0(t4)
	sw	t5,548(sp)
	lw	t4,628(sp)
	lw	t5,0(t4)
	sw	t5,544(sp)
	lw	t3,548(sp)
	lw	t4,544(sp)
	sub	t5,t3,t4
	sw	t5,540(sp)
	lw	t3,540(sp)
	sltz	t5,t3
	sw	t5,536(sp)
	lw	t3,536(sp)
	beqz	t3,.for.0.end
	j	.for.0.body
.for.0.body:
	lw	t4,604(sp)
	lw	t5,0(t4)
	sw	t5,532(sp)
	lw	t4,612(sp)
	lw	t5,0(t4)
	sw	t5,528(sp)
	li	t3,4
	sw	t3,524(sp)
	lw	t3,528(sp)
	lw	t4,524(sp)
	mul	t5,t3,t4
	sw	t5,520(sp)
	lw	t3,532(sp)
	lw	t4,520(sp)
	add	t5,t3,t4
	sw	t5,516(sp)
	call	__getInt__
	mv	t5,a0
	sw	t5,512(sp)
	lw	t4,516(sp)
	lw	t3,512(sp)
	sw	t3,0(t4)
	lw	t4,588(sp)
	lw	t5,0(t4)
	sw	t5,508(sp)
	lw	t4,612(sp)
	lw	t5,0(t4)
	sw	t5,504(sp)
	li	t3,4
	sw	t3,500(sp)
	lw	t3,504(sp)
	lw	t4,500(sp)
	mul	t5,t3,t4
	sw	t5,496(sp)
	lw	t3,508(sp)
	lw	t4,496(sp)
	add	t5,t3,t4
	sw	t5,492(sp)
	li	t3,0
	sw	t3,488(sp)
	lw	t4,492(sp)
	lw	t3,488(sp)
	sw	t3,0(t4)
	j	.for.0.step
.for.0.step:
	lw	t4,612(sp)
	lw	t5,0(t4)
	sw	t5,484(sp)
	li	t3,1
	sw	t3,480(sp)
	lw	t3,484(sp)
	lw	t4,480(sp)
	add	t5,t3,t4
	sw	t5,476(sp)
	lw	t4,612(sp)
	lw	t3,476(sp)
	sw	t3,0(t4)
	j	.for.0.condition
.for.0.end:
	lw	t4,572(sp)
	lw	t5,0(t4)
	sw	t5,472(sp)
	li	t3,0
	sw	t3,468(sp)
	li	t3,4
	sw	t3,464(sp)
	lw	t3,468(sp)
	lw	t4,464(sp)
	mul	t5,t3,t4
	sw	t5,460(sp)
	lw	t3,472(sp)
	lw	t4,460(sp)
	add	t5,t3,t4
	sw	t5,456(sp)
	li	t3,1
	sw	t3,452(sp)
	lw	t4,456(sp)
	lw	t3,452(sp)
	sw	t3,0(t4)
	j	.while.0.condition
.while.0.condition:
	lw	t4,656(sp)
	lw	t5,0(t4)
	sw	t5,448(sp)
	lw	t4,620(sp)
	lw	t5,0(t4)
	sw	t5,444(sp)
	lw	t3,448(sp)
	lw	t4,444(sp)
	sub	t5,t3,t4
	sw	t5,440(sp)
	lw	t3,440(sp)
	sgtz	t5,t3
	sw	t5,436(sp)
	lw	t3,436(sp)
	xori	t5,t3,1
	sw	t5,432(sp)
	lw	t3,432(sp)
	beqz	t3,.while.0.end
	j	.while.0.body
.while.0.body:
	li	t3,2139062143
	sw	t3,428(sp)
	lw	t4,636(sp)
	lw	t3,428(sp)
	sw	t3,0(t4)
	li	t3,0
	sw	t3,424(sp)
	lw	t4,612(sp)
	lw	t3,424(sp)
	sw	t3,0(t4)
	j	.for.1.condition
.for.1.condition:
	lw	t4,612(sp)
	lw	t5,0(t4)
	sw	t5,420(sp)
	lw	t4,628(sp)
	lw	t5,0(t4)
	sw	t5,416(sp)
	lw	t3,420(sp)
	lw	t4,416(sp)
	sub	t5,t3,t4
	sw	t5,412(sp)
	lw	t3,412(sp)
	sltz	t5,t3
	sw	t5,408(sp)
	lw	t3,408(sp)
	beqz	t3,.for.1.end
	j	.for.1.body
.for.1.body:
	j	.while.1.condition
.while.1.condition:
	lw	t4,572(sp)
	lw	t5,0(t4)
	sw	t5,404(sp)
	lw	t4,656(sp)
	lw	t5,0(t4)
	sw	t5,400(sp)
	li	t3,4
	sw	t3,396(sp)
	lw	t3,400(sp)
	lw	t4,396(sp)
	mul	t5,t3,t4
	sw	t5,392(sp)
	lw	t3,404(sp)
	lw	t4,392(sp)
	add	t5,t3,t4
	sw	t5,388(sp)
	lw	t4,388(sp)
	lw	t5,0(t4)
	sw	t5,384(sp)
	lw	t4,604(sp)
	lw	t5,0(t4)
	sw	t5,380(sp)
	lw	t4,612(sp)
	lw	t5,0(t4)
	sw	t5,376(sp)
	li	t3,4
	sw	t3,372(sp)
	lw	t3,376(sp)
	lw	t4,372(sp)
	mul	t5,t3,t4
	sw	t5,368(sp)
	lw	t3,380(sp)
	lw	t4,368(sp)
	add	t5,t3,t4
	sw	t5,364(sp)
	lw	t4,364(sp)
	lw	t5,0(t4)
	sw	t5,360(sp)
	lw	t4,572(sp)
	lw	t5,0(t4)
	sw	t5,356(sp)
	lw	t4,588(sp)
	lw	t5,0(t4)
	sw	t5,352(sp)
	lw	t4,612(sp)
	lw	t5,0(t4)
	sw	t5,348(sp)
	li	t3,4
	sw	t3,344(sp)
	lw	t3,348(sp)
	lw	t4,344(sp)
	mul	t5,t3,t4
	sw	t5,340(sp)
	lw	t3,352(sp)
	lw	t4,340(sp)
	add	t5,t3,t4
	sw	t5,336(sp)
	lw	t4,336(sp)
	lw	t5,0(t4)
	sw	t5,332(sp)
	li	t3,4
	sw	t3,328(sp)
	lw	t3,332(sp)
	lw	t4,328(sp)
	mul	t5,t3,t4
	sw	t5,324(sp)
	lw	t3,356(sp)
	lw	t4,324(sp)
	add	t5,t3,t4
	sw	t5,320(sp)
	lw	t4,320(sp)
	lw	t5,0(t4)
	sw	t5,316(sp)
	lw	t3,360(sp)
	lw	t4,316(sp)
	mul	t5,t3,t4
	sw	t5,312(sp)
	lw	t3,384(sp)
	lw	t4,312(sp)
	sub	t5,t3,t4
	sw	t5,308(sp)
	lw	t3,308(sp)
	sltz	t5,t3
	sw	t5,304(sp)
	lw	t3,304(sp)
	xori	t5,t3,1
	sw	t5,300(sp)
	lw	t3,300(sp)
	beqz	t3,.while.1.end
	j	.while.1.body
.while.1.body:
	lw	t4,588(sp)
	lw	t5,0(t4)
	sw	t5,296(sp)
	lw	t4,612(sp)
	lw	t5,0(t4)
	sw	t5,292(sp)
	li	t3,4
	sw	t3,288(sp)
	lw	t3,292(sp)
	lw	t4,288(sp)
	mul	t5,t3,t4
	sw	t5,284(sp)
	lw	t3,296(sp)
	lw	t4,284(sp)
	add	t5,t3,t4
	sw	t5,280(sp)
	lw	t4,280(sp)
	lw	t5,0(t4)
	sw	t5,276(sp)
	li	t3,1
	sw	t3,272(sp)
	lw	t3,276(sp)
	lw	t4,272(sp)
	add	t5,t3,t4
	sw	t5,268(sp)
	lw	t4,588(sp)
	lw	t5,0(t4)
	sw	t5,264(sp)
	lw	t4,612(sp)
	lw	t5,0(t4)
	sw	t5,260(sp)
	li	t3,4
	sw	t3,256(sp)
	lw	t3,260(sp)
	lw	t4,256(sp)
	mul	t5,t3,t4
	sw	t5,252(sp)
	lw	t3,264(sp)
	lw	t4,252(sp)
	add	t5,t3,t4
	sw	t5,248(sp)
	lw	t4,248(sp)
	lw	t3,268(sp)
	sw	t3,0(t4)
	j	.while.1.condition
.while.1.end:
	lw	t4,604(sp)
	lw	t5,0(t4)
	sw	t5,244(sp)
	lw	t4,612(sp)
	lw	t5,0(t4)
	sw	t5,240(sp)
	li	t3,4
	sw	t3,236(sp)
	lw	t3,240(sp)
	lw	t4,236(sp)
	mul	t5,t3,t4
	sw	t5,232(sp)
	lw	t3,244(sp)
	lw	t4,232(sp)
	add	t5,t3,t4
	sw	t5,228(sp)
	lw	t4,228(sp)
	lw	t5,0(t4)
	sw	t5,224(sp)
	lw	t4,572(sp)
	lw	t5,0(t4)
	sw	t5,220(sp)
	lw	t4,588(sp)
	lw	t5,0(t4)
	sw	t5,216(sp)
	lw	t4,612(sp)
	lw	t5,0(t4)
	sw	t5,212(sp)
	li	t3,4
	sw	t3,208(sp)
	lw	t3,212(sp)
	lw	t4,208(sp)
	mul	t5,t3,t4
	sw	t5,204(sp)
	lw	t3,216(sp)
	lw	t4,204(sp)
	add	t5,t3,t4
	sw	t5,200(sp)
	lw	t4,200(sp)
	lw	t5,0(t4)
	sw	t5,196(sp)
	li	t3,4
	sw	t3,192(sp)
	lw	t3,196(sp)
	lw	t4,192(sp)
	mul	t5,t3,t4
	sw	t5,188(sp)
	lw	t3,220(sp)
	lw	t4,188(sp)
	add	t5,t3,t4
	sw	t5,184(sp)
	lw	t4,184(sp)
	lw	t5,0(t4)
	sw	t5,180(sp)
	lw	t3,224(sp)
	lw	t4,180(sp)
	mul	t5,t3,t4
	sw	t5,176(sp)
	lw	t4,636(sp)
	lw	t5,0(t4)
	sw	t5,172(sp)
	lw	t3,176(sp)
	lw	t4,172(sp)
	sub	t5,t3,t4
	sw	t5,168(sp)
	lw	t3,168(sp)
	sltz	t5,t3
	sw	t5,164(sp)
	lw	t3,164(sp)
	beqz	t3,.if.0.end
	j	.if.0.then
.if.0.then:
	lw	t4,604(sp)
	lw	t5,0(t4)
	sw	t5,160(sp)
	lw	t4,612(sp)
	lw	t5,0(t4)
	sw	t5,156(sp)
	li	t3,4
	sw	t3,152(sp)
	lw	t3,156(sp)
	lw	t4,152(sp)
	mul	t5,t3,t4
	sw	t5,148(sp)
	lw	t3,160(sp)
	lw	t4,148(sp)
	add	t5,t3,t4
	sw	t5,144(sp)
	lw	t4,144(sp)
	lw	t5,0(t4)
	sw	t5,140(sp)
	lw	t4,572(sp)
	lw	t5,0(t4)
	sw	t5,136(sp)
	lw	t4,588(sp)
	lw	t5,0(t4)
	sw	t5,132(sp)
	lw	t4,612(sp)
	lw	t5,0(t4)
	sw	t5,128(sp)
	li	t3,4
	sw	t3,124(sp)
	lw	t3,128(sp)
	lw	t4,124(sp)
	mul	t5,t3,t4
	sw	t5,120(sp)
	lw	t3,132(sp)
	lw	t4,120(sp)
	add	t5,t3,t4
	sw	t5,116(sp)
	lw	t4,116(sp)
	lw	t5,0(t4)
	sw	t5,112(sp)
	li	t3,4
	sw	t3,108(sp)
	lw	t3,112(sp)
	lw	t4,108(sp)
	mul	t5,t3,t4
	sw	t5,104(sp)
	lw	t3,136(sp)
	lw	t4,104(sp)
	add	t5,t3,t4
	sw	t5,100(sp)
	lw	t4,100(sp)
	lw	t5,0(t4)
	sw	t5,96(sp)
	lw	t3,140(sp)
	lw	t4,96(sp)
	mul	t5,t3,t4
	sw	t5,92(sp)
	lw	t4,636(sp)
	lw	t3,92(sp)
	sw	t3,0(t4)
	lw	t4,612(sp)
	lw	t5,0(t4)
	sw	t5,88(sp)
	lw	t4,644(sp)
	lw	t3,88(sp)
	sw	t3,0(t4)
	j	.if.0.end
.if.0.else:
	j	.if.0.end
.if.0.end:
	j	.for.1.step
.for.1.step:
	lw	t4,612(sp)
	lw	t5,0(t4)
	sw	t5,84(sp)
	li	t3,1
	sw	t3,80(sp)
	lw	t3,84(sp)
	lw	t4,80(sp)
	add	t5,t3,t4
	sw	t5,76(sp)
	lw	t4,612(sp)
	lw	t3,76(sp)
	sw	t3,0(t4)
	j	.for.1.condition
.for.1.end:
	lw	t4,572(sp)
	lw	t5,0(t4)
	sw	t5,72(sp)
	lw	t4,656(sp)
	lw	t5,0(t4)
	sw	t5,68(sp)
	li	t3,1
	sw	t3,64(sp)
	lw	t3,68(sp)
	lw	t4,64(sp)
	add	t5,t3,t4
	sw	t5,60(sp)
	lw	t4,656(sp)
	lw	t3,60(sp)
	sw	t3,0(t4)
	li	t3,4
	sw	t3,56(sp)
	lw	t3,60(sp)
	lw	t4,56(sp)
	mul	t5,t3,t4
	sw	t5,52(sp)
	lw	t3,72(sp)
	lw	t4,52(sp)
	add	t5,t3,t4
	sw	t5,48(sp)
	lw	t4,636(sp)
	lw	t5,0(t4)
	sw	t5,44(sp)
	lw	t4,48(sp)
	lw	t3,44(sp)
	sw	t3,0(t4)
	j	.while.0.condition
.while.0.end:
	lw	t4,572(sp)
	lw	t5,0(t4)
	sw	t5,40(sp)
	lw	t4,620(sp)
	lw	t5,0(t4)
	sw	t5,36(sp)
	li	t3,4
	sw	t3,32(sp)
	lw	t3,36(sp)
	lw	t4,32(sp)
	mul	t5,t3,t4
	sw	t5,28(sp)
	lw	t3,40(sp)
	lw	t4,28(sp)
	add	t5,t3,t4
	sw	t5,24(sp)
	lw	t4,24(sp)
	lw	t5,0(t4)
	sw	t5,20(sp)
	lw	t3,20(sp)
	mv	a0,t3
	call	__toString__
	mv	t5,a0
	sw	t5,16(sp)
	lw	t3,16(sp)
	mv	a0,t3
	call	__print__
	mv	t5,a0
	sw	t5,12(sp)
	li	t3,0
	sw	t3,8(sp)
	lw	t3,8(sp)
	mv	a0,t3
	j	.main.exit
.main.exit:
	lw	ra,716(sp)
	addi	sp,sp,720
	jr	ra
